package com.cnce.manager.tools.service.impl;

import com.cnce.common.constant.ToolsConstant;
import com.cnce.common.domain.JenkinsJobInfo;
import com.cnce.common.jenkins.JenkinsConstant;
import com.cnce.common.jenkins.JenkinsFilter;
import com.cnce.common.jenkins.JenkinsUtil;
import com.cnce.common.utils.TimeUtils;
import com.cnce.manager.logs.dao.LogsDao;
import com.cnce.manager.logs.domain.GenLogsDO;
import com.cnce.manager.logs.domain.LogsDO;
import com.cnce.manager.report.dao.ReportDao;
import com.cnce.manager.report.domain.GenReportDO;
import com.cnce.manager.report.domain.ReportDO;
import com.cnce.manager.tools.service.PDUService;
import com.cnce.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PDUServiceImpl implements PDUService {

	private String jobName = JenkinsConstant.PDU_ONOFF;

	JenkinsUtil jenkins = new JenkinsUtil();

	@Autowired
	private LogsDao logMapper;
	@Autowired
	private ReportDao reportMapper;

	@Override
	public boolean beginTask(Map<String, String> params) {
		try {
			params.put("labelName", JenkinsFilter.getHardNode("devName"));
			//params.put("labelName", "test_node_chen");
			String token = JenkinsJobInfo.JobName.JOB12.toString();
			params.put("token", token);
			params.put("jobName", jobName);
			// 开始build job
			jenkins.buildParamJob(jobName, token, params);
			TimeUtils.timeSleep(10);
			while (true) {
				List<Integer> builds = jenkins.getJobBuildListAll(jobName);
				boolean hasEnd = jenkins.isFinished(jobName, builds.get(0));
				if (hasEnd) {
					break;
				}
				TimeUtils.timeSleep(10);
			}
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
			return true;
	}

	@Override
	public boolean cancelTask(String devName) {
		List<Integer> jobList = jenkins.getJobBuildListAll(jobName);
		boolean isFinished = jenkins.isFinished(jobName, jobList.get(0));
		if (!isFinished) {
			return jenkins.stopJenkinsJob(jobName);
		} else {
			return true;
		}
	}

	@Override
	public int saveLogs(UserDO user) {
		List<Integer> bs = jenkins.getJobBuildListAll(jobName);
		String result = jenkins.getBuildResult(bs.get(0), jobName);
		Integer status = JenkinsFilter.getResCode(result);
		LogsDO logDO = GenLogsDO.loadDO(user, ToolsConstant.PDU_INP_ID, status, ToolsConstant.PDU_INP,
				ToolsConstant.PDU_LOG_PATH);
		return logMapper.save(logDO);
	}

	@Override
	public int saveResult(UserDO user) {
		ReportDO reportDO = GenReportDO.loadDO(user, ToolsConstant.PDU_INP_ID, ToolsConstant.PDU_INP,
				ToolsConstant.PDU_REPORT_PATH);
		return reportMapper.save(reportDO);
	}

}
