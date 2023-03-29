package com.cnce.manager.tools.service.impl;

import com.cnce.common.constant.ToolsConstant;
import com.cnce.common.domain.JenkinsJobInfo;
import com.cnce.common.jenkins.JenkinsConstant;
import com.cnce.common.jenkins.JenkinsFilter;
import com.cnce.common.jenkins.JenkinsUtil;
import com.cnce.common.utils.DateUtils;
import com.cnce.common.utils.TimeUtils;
import com.cnce.manager.logs.dao.LogsDao;
import com.cnce.manager.logs.domain.GenLogsDO;
import com.cnce.manager.logs.domain.LogsDO;
import com.cnce.manager.manpower.dao.ManpowerDao;
import com.cnce.manager.manpower.domain.ManpowerDO;
import com.cnce.manager.report.dao.ReportDao;
import com.cnce.manager.report.domain.GenReportDO;
import com.cnce.manager.report.domain.ReportDO;
import com.cnce.manager.tools.dao.HeartbeatAndPretestBlueDao;
import com.cnce.manager.tools.domain.HeartbeatAndPretestBlueDO;
import com.cnce.manager.tools.service.HeartbeatAndPretestBlueService;
import com.cnce.system.domain.UserDO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;


@Service
public class HeartbeatAndPretestBlueServiceImpl implements HeartbeatAndPretestBlueService {

	private String jobName = JenkinsConstant.HB_7502_PRETEST;

	JenkinsUtil jenkins = new JenkinsUtil();

	@Autowired
	private HeartbeatAndPretestBlueDao heartbeatAndPretestBlueMapper;
	@Autowired
	private LogsDao logMapper;
	@Autowired
	private ReportDao reportMapper;
	@Autowired
	private ManpowerDao manpowerMapper;


	public List<HeartbeatAndPretestBlueDO> getAllAppInfo(Map<String, Object> map) {
		List<HeartbeatAndPretestBlueDO> list = heartbeatAndPretestBlueMapper.getAllAppInfo(map);
		return list;
	}

	public int count(Map<String, Object> map) {
		return heartbeatAndPretestBlueMapper.count(map);
	}

	public String startTest(String[] names, int num, String equipName, UserDO user) {
		Map<String,String> params =new HashMap<String,String>();
		if (names.length > 0) {
			params.put("testCase", StringUtils.join(names, ","));
			params.put("runTime", String.valueOf(num));
			params.put("labelName", JenkinsFilter.getSoftNode(equipName));
			String token = JenkinsJobInfo.JobName.JOB4.toString();

			// 开始使用时间
			Date statDate = new Date();
			jenkins.buildParamJob(jobName, token, params);
			TimeUtils.timeSleep(10);
			while (true){
				List<Integer> builds = jenkins.getJobBuildListAll(jobName);
				boolean hasEnd = jenkins.isFinished(jobName, builds.get(0));
				if(hasEnd){
					break;
				}
				TimeUtils.timeSleep(10);
			}

			// 检查测试结果是否有效
			List<Integer> bs = jenkins.getJobBuildListAll(jobName);
			String result = jenkins.getBuildResult(bs.get(0), jobName);
			Integer status = JenkinsFilter.getResCode(result);
			if(status == 1){
				// 工具的人力数据入库
				List<ManpowerDO> manList = new ArrayList<>();
				for(int i=0; i<3; i++){
					ManpowerDO manpower = new ManpowerDO();
					manpower.setToolId(ToolsConstant.H_7502_ID);
					manpower.setToolName(ToolsConstant.H_7502_NAME);
					manpower.setUseDept(ToolsConstant.sDept);
					manpower.setStartUseTime(statDate);
					manpower.setEndUseTime(new Date());
					int minutes = DateUtils.getBetweenMinutes(statDate, new Date());
					DecimalFormat df = new DecimalFormat("0.00");
					manpower.setUseTime(String.valueOf(df.format((float)minutes/60)));
					manpower.setCreateTime(new Date());
					manpower.setFunName("执行次数");
					manList.add(manpower);
					manpower.setFunName("用例选择");
					manList.add(manpower);
					manpower.setFunName("开始测试");
					manList.add(manpower);
				}
				manpowerMapper.insertList(manList);
			}
			// 测试结果入库
			LogsDO logDO = GenLogsDO.loadDO(user, ToolsConstant.H_7502_ID, status, ToolsConstant.H_7502_NAME,
					ToolsConstant.G_7502_LOG_PATH);
			logMapper.save(logDO);
			ReportDO reportDO = GenReportDO.loadDO(user, ToolsConstant.H_7502_ID, ToolsConstant.H_7502_NAME,
					ToolsConstant.G_7502_REPORT_PATH);
			reportMapper.save(reportDO);
		}
		return "SUCCESS";
	}

	public boolean cancelTest(boolean flag,String equipName) {
		// 开始使用时间
		Date statDate = new Date();
		List<Integer> list = jenkins.getJobBuildListAll(jobName);
		// 工具的人力数据入库
		ManpowerDO manpower = new ManpowerDO();
		manpower.setToolId(ToolsConstant.H_7502_ID);
		manpower.setToolName(ToolsConstant.H_7502_NAME);
		manpower.setUseDept(ToolsConstant.sDept);
		manpower.setStartUseTime(statDate);
		manpower.setEndUseTime(new Date());
		int minutes = DateUtils.getBetweenMinutes(statDate, new Date());
		DecimalFormat df = new DecimalFormat("0.00");
		manpower.setUseTime(String.valueOf(df.format((float)minutes/60)));
		manpower.setCreateTime(new Date());
		manpower.setFunName("取消测试");
		manpowerMapper.save(manpower);

		boolean isFinished = jenkins.isFinished(jobName, list.get(0));
		if(!isFinished){
			return jenkins.stopJenkinsJob(jobName);
		}else {
			return true;
		}
	}

}
