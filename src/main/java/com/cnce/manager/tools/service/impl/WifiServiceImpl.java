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
import com.cnce.manager.profit.dao.ProfitDao;
import com.cnce.manager.report.dao.ReportDao;
import com.cnce.manager.report.domain.GenReportDO;
import com.cnce.manager.report.domain.ReportDO;
import com.cnce.manager.tools.domain.WifiDO;
import com.cnce.manager.tools.service.WifiService;
import com.cnce.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class WifiServiceImpl implements WifiService {

	private String jobName = JenkinsConstant.WIFI_INTERFERE;

	JenkinsUtil jenkins = new JenkinsUtil();

	@Autowired
	private LogsDao logMapper;
	@Autowired
	private ReportDao reportMapper;
	@Autowired
	private ManpowerDao manpowerMapper;

	@Override
	public boolean beginTask(WifiDO wifiDO) {
		Map<String, String> params = new HashMap<>();
		params.put("projectName", wifiDO.getProjectName());
		params.put("testStage", wifiDO.getTestStage());
		params.put("testPlan", wifiDO.getTestPlan());
		params.put("engineer", wifiDO.getEngineer());
		params.put("device", wifiDO.getDevice());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = format.format(wifiDO.getTestTime());
		params.put("testTime", strDate);
		params.put("initLevel", wifiDO.getInitLevel());
		params.put("power", wifiDO.getPower());
		params.put("lineLoss", wifiDO.getLineLoss());
		params.put("scenario", wifiDO.getScenario());
		params.put("network", wifiDO.getNetwork());
		params.put("spectrum", wifiDO.getSpectrum());
		params.put("channel", wifiDO.getChannel());
		params.put("labelName", JenkinsFilter.getHardNode(wifiDO.getEquipName()));
		String token = JenkinsJobInfo.JobName.JOB10.toString();

		// 开始使用时间
		Date statDate = new Date();
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

		List<Integer> bs = jenkins.getJobBuildListAll(jobName);
		String result = jenkins.getBuildResult(bs.get(0), jobName);
		Integer status = JenkinsFilter.getResCode(result);
		if(status == 1){
			// 工具的人力数据入库
			List<String> scenario = Arrays.asList(wifiDO.getScenario().split(","));
			List<String> network = Arrays.asList(wifiDO.getNetwork().split(","));
			List<String> spectrum = Arrays.asList(wifiDO.getSpectrum().split(","));
			List<String> channel = Arrays.asList(wifiDO.getChannel().split(","));
			int num = scenario.size()+network.size()+spectrum.size()+channel.size();
			List<ManpowerDO>  manList = new ArrayList<>();
			for(int i=0; i<num; i++){
				ManpowerDO manpower = new ManpowerDO();
				manpower.setToolId(ToolsConstant.WIFI_ID);
				manpower.setToolName(ToolsConstant.WIFI_NAME);
				manpower.setUseDept(ToolsConstant.hDept);
				manpower.setFunName("测试提交");
				manpower.setStartUseTime(statDate);
				manpower.setEndUseTime(new Date());
				int minutes = DateUtils.getBetweenMinutes(statDate, new Date());
				DecimalFormat df = new DecimalFormat("0.00");
				manpower.setUseTime(String.valueOf(df.format((float)minutes/60)));
				manpower.setCreateTime(new Date());
				manList.add(manpower);
			}
			manpowerMapper.insertList(manList);
		}
		return true;
	}

	@Override
	public int saveLogs(UserDO user) {
		List<Integer> bs = jenkins.getJobBuildListAll(jobName);
		String result = jenkins.getBuildResult(bs.get(0), jobName);
		Integer status = JenkinsFilter.getResCode(result);
		LogsDO logDO = GenLogsDO.loadDO(user, ToolsConstant.WIFI_ID, status, ToolsConstant.WIFI_NAME,
				ToolsConstant.WIFI_LOG_PATH);
		return logMapper.save(logDO);
	}

	@Override
	public int saveResult(UserDO user) {
		ReportDO reportDO = GenReportDO.loadDO(user, ToolsConstant.WIFI_ID, ToolsConstant.WIFI_NAME,
				ToolsConstant.WIFI_REPORT_PATH);
		return reportMapper.save(reportDO);
	}

}
