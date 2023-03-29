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
import com.cnce.manager.tools.filter.Camera3AFilter;
import com.cnce.manager.tools.service.Camera3AService;
import com.cnce.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class Camera3AServiceImpl implements Camera3AService {

    JenkinsUtil jenkins = new JenkinsUtil();

    private String jobName = JenkinsConstant.CMA_OBJECTIVE;
    @Autowired
    private LogsDao logMapper;
    @Autowired
    private ReportDao reportMapper;
    @Autowired
    private ManpowerDao manpowerMapper;

    @Override
    public String startTask(Map<String, String> param, UserDO user) {
        if("单模式拍摄(20张)".equals(param.get("cameraMode"))){
            param.put("cameraMode", "1");
        }else if("双模式拍摄(40张)".equals(param.get("cameraMode"))){
            param.put("cameraMode", "2");
        }
        param.put("cameraLens", Camera3AFilter.getCameraLens(param.get("cameraLens")));
        param.put("cameraTime", Camera3AFilter.getCameraIntervals(param.get("cameraTime")));
        param.put("labelName", JenkinsFilter.getHardNode(param.get("equipName")));

        String token = JenkinsJobInfo.JobName.JOB1.toString();
        param.put("token", token);
        String jobName = user.getUsername() + "_" + JenkinsConstant.CMA_OBJECTIVE;
        param.put("jobName", jobName);

        // 开始使用时间
        Date statDate = new Date();
        // 开始build job
        if(jenkins.isJenkinsJobExist(jobName)){
            jenkins.deleteJob(jobName);
        }
        jenkins.createCM3AJob(param);
        jenkins.buildJob(jobName);
        TimeUtils.timeSleep(10);
        while (true) {
            List<Integer> builds = jenkins.getJobBuildListAll(jobName);
            boolean hasEnd = jenkins.isFinished(jobName, builds.get(0));
            if (hasEnd) {
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
            ManpowerDO mp = new ManpowerDO();
            mp.setToolId(ToolsConstant.CMA3A_ID);
            mp.setToolName(ToolsConstant.CMA3A_NAME);
            mp.setUseDept(ToolsConstant.hDept);
            if("1".equals(param.get("cameraMode"))){
                mp.setFunName("单模式拍摄(20张)");
            }else if("2".equals(param.get("cameraMode"))){
                mp.setFunName("双模式拍摄(40张)");
            }
            mp.setStartUseTime(statDate);
            mp.setEndUseTime(new Date());
            int minutes = DateUtils.getBetweenMinutes(statDate, new Date());
            DecimalFormat df = new DecimalFormat("0.00");
            mp.setUseTime(String.valueOf(df.format((float)minutes/60)));
            mp.setCreateTime(new Date());
            manpowerMapper.save(mp);
        }
        // 测试结果入库
        LogsDO logDO = GenLogsDO.loadDO(user, ToolsConstant.CMA3A_ID, status, ToolsConstant.CMA3A_NAME,
                ToolsConstant.CMA3A_LOG_PATH);
        logMapper.save(logDO);
        ReportDO reportDO = GenReportDO.loadDO(user, ToolsConstant.CMA3A_ID, ToolsConstant.CMA3A_NAME,
                ToolsConstant.CMA3A_REPORT_PATH);
        reportMapper.save(reportDO);

        return "SUCCESS";
    }

    @Override
    public boolean cancelTask(String equipName) {
        List<Integer> list = jenkins.getJobBuildListAll(jobName);
        boolean isFinished = jenkins.isFinished(jobName, list.get(0));
        if (!isFinished) {
            return jenkins.stopJenkinsJob(jobName);
        } else {
            return true;
        }
    }

}