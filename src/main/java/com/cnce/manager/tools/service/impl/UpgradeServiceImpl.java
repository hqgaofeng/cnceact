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
import com.cnce.manager.tools.dao.UpgradeDao;
import com.cnce.manager.tools.service.UpgradeService;
import com.cnce.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UpgradeServiceImpl implements UpgradeService {

	private String jobName = JenkinsConstant.F_MULTI_UPGRADE;

	String token = JenkinsJobInfo.JobName.JOB7.toString();

	JenkinsUtil jenkins = new JenkinsUtil();

	@Autowired
	private UpgradeDao upgradeMapper;
	@Autowired
	private LogsDao logMapper;
	@Autowired
	private ReportDao reportMapper;
	@Autowired
	private ManpowerDao manpowerMapper;

	public String onlyUpgrade(String localPath,String platformType, String packType, String equipName, UserDO user){
		Map<String,String> params = new HashMap<>();
		params.put("reqModel", "upgrade");
		params.put("localPath", localPath);
		params.put("platformType", platformType);
		params.put("executeType", packType);
		params.put("labelName", JenkinsFilter.getSoftNode(equipName));
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
			ManpowerDO manpower = new ManpowerDO();
			manpower.setToolId(ToolsConstant.B_UPAPP_ID);
			manpower.setToolName(ToolsConstant.B_UPAPP_NAME);
			manpower.setUseDept(ToolsConstant.sDept);
			manpower.setFunName("升级");
			manpower.setStartUseTime(statDate);
			manpower.setEndUseTime(new Date());
			int minutes = DateUtils.getBetweenMinutes(statDate, new Date());
			DecimalFormat df = new DecimalFormat("0.00");
			manpower.setUseTime(String.valueOf(df.format((float)minutes/60)));
			manpower.setCreateTime(new Date());
			manpowerMapper.save(manpower);
		}
		// 测试结果入库
		LogsDO logDO = GenLogsDO.loadDO(user, ToolsConstant.F_UPAPP_ID, status, ToolsConstant.F_UPAPP_NAME,
				ToolsConstant.F_UPGRADE_LOG_PATH);
		logMapper.save(logDO);
		ReportDO reportDO = GenReportDO.loadDO(user, ToolsConstant.F_UPAPP_ID, ToolsConstant.F_UPAPP_NAME,
				ToolsConstant.F_UPGRADE_REPORT_PATH);
		reportMapper.save(reportDO);
		return  "SUCCESS";
	}

	public String  upgradeAndRoot(String localPath, String platformType, String packType, String equipName, UserDO user){
		Map<String,String> params = new HashMap<>();
		params.put("reqModel", "updAndRoot");
		params.put("localPath", localPath);
		params.put("executeType", packType);
		params.put("labelName", JenkinsFilter.getSoftNode(equipName));
		if ("Qualcomm".equals(platformType)){
			params.put("platformType", "Qualcomm");
		}else if ("MTK".equals(platformType)){
			params.put("platformType", "MTK");
		}
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
			ManpowerDO manpower = new ManpowerDO();
			manpower.setToolId(ToolsConstant.B_UPAPP_ID);
			manpower.setToolName(ToolsConstant.B_UPAPP_NAME);
			manpower.setUseDept(ToolsConstant.sDept);
			manpower.setFunName("升级root");
			manpower.setStartUseTime(statDate);
			manpower.setEndUseTime(new Date());
			int minutes = DateUtils.getBetweenMinutes(statDate, new Date());
			DecimalFormat df = new DecimalFormat("0.00");
			manpower.setUseTime(String.valueOf(df.format((float)minutes/60)));
			manpower.setCreateTime(new Date());
			manpowerMapper.save(manpower);
		}
		// 测试结果入库
		LogsDO logDO = GenLogsDO.loadDO(user, ToolsConstant.F_UPAPP_ID, status, ToolsConstant.F_UPAPP_NAME,
				ToolsConstant.F_UPGRADE_LOG_PATH);
		logMapper.save(logDO);
		ReportDO reportDO = GenReportDO.loadDO(user, ToolsConstant.F_UPAPP_ID, ToolsConstant.F_UPAPP_NAME,
				ToolsConstant.F_UPGRADE_REPORT_PATH);
		reportMapper.save(reportDO);
		return  "SUCCESS";
	}

	public String skipWizad(String equipName, UserDO user){
		Map<String,String> params = new HashMap<>();
		params.put("reqModel", "skipWizard");
		params.put("labelName", JenkinsFilter.getSoftNode(equipName));
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
			ManpowerDO manpower = new ManpowerDO();
			manpower.setToolId(ToolsConstant.B_UPAPP_ID);
			manpower.setToolName(ToolsConstant.B_UPAPP_NAME);
			manpower.setUseDept(ToolsConstant.sDept);
			manpower.setFunName("跳过开机向导");
			manpower.setStartUseTime(statDate);
			manpower.setEndUseTime(new Date());
			int minutes = DateUtils.getBetweenMinutes(statDate, new Date());
			DecimalFormat df = new DecimalFormat("0.00");
			manpower.setUseTime(String.valueOf(df.format((float)minutes/60)));
			manpower.setCreateTime(new Date());
			manpowerMapper.save(manpower);
		}
		// 测试结果入库
		LogsDO logDO = GenLogsDO.loadDO(user, ToolsConstant.F_UPAPP_ID, status, ToolsConstant.F_UPAPP_NAME,
				ToolsConstant.F_UPGRADE_LOG_PATH);
		logMapper.save(logDO);
		ReportDO reportDO = GenReportDO.loadDO(user, ToolsConstant.F_UPAPP_ID, ToolsConstant.F_UPAPP_NAME,
				ToolsConstant.F_UPGRADE_REPORT_PATH);
		reportMapper.save(reportDO);
		return  "SUCCESS";
	}

}
