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
import com.cnce.manager.tools.service.MachineService;
import com.cnce.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MachineServiceImpl implements MachineService {

	JenkinsUtil jenkins = new JenkinsUtil();

	private String jobName = JenkinsConstant.MACH_INTERFERE;

	@Autowired
	private LogsDao logMapper;
	@Autowired
	private ReportDao reportMapper;

	@Override
	public boolean beginTask(Map<String, String> params) {
		String nodeName = params.get("labelName");
		params.put("labelName", JenkinsFilter.getHardNode(nodeName));
		String token = JenkinsJobInfo.JobName.JOB13.toString();
		params.put("token", token);
		params.put("jobName", jobName);
		// 开始build job
		jenkins.buildParamJob(jobName, token, params);
		TimeUtils.timeSleep(10);
		while (true) {
			List<Integer> builds = jenkins.getJobBuildListAll(jobName);
			boolean hasEnd = jenkins.isFinished(jobName, builds.get(0));
			if (hasEnd) {
				return true;
			}
			TimeUtils.timeSleep(10);
		}
	}

	@Override
	public int saveLogs(UserDO user) {
		List<Integer> bs = jenkins.getJobBuildListAll(jobName);
		String result = jenkins.getBuildResult(bs.get(0), jobName);
		Integer status = JenkinsFilter.getResCode(result);
		LogsDO logs = GenLogsDO.loadDO(user, ToolsConstant.MACHINE_ID, status,
				ToolsConstant.MACHINE, ToolsConstant.MACHINE_LOG);
		return logMapper.save(logs);
	}

	@Override
	public int saveResult(UserDO user) {
		ReportDO report = GenReportDO.loadDO(user, ToolsConstant.MACHINE_ID,
				ToolsConstant.MACHINE, ToolsConstant.MACHINE_REPORT);
		return reportMapper.save(report);
	}

}
