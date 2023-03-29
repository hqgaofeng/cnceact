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
import com.cnce.manager.tools.service.FlareService;
import com.cnce.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class FlareServiceImpl implements FlareService {

    JenkinsUtil jenkins = new JenkinsUtil();

    @Autowired
    private LogsDao logMapper;
    @Autowired
    private ReportDao reportMapper;
    @Autowired
    private ManpowerDao manpowerMapper;

    public String  startTask(String projectName, String cameraLens, String testScene, String path,
                             String name, String number, String equipName, UserDO user) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("projectName", projectName);
        params.put("cameraLens", cameraLens);
        params.put("scenes", testScene);
        String directory = path.replaceAll("\\\\", "/");
        params.put("directory", directory);
        String excelPath = name.replaceAll("\\\\", "/");
        params.put("excelPath", excelPath);
        params.put("deviceNo", number);
        params.put("labelName", JenkinsFilter.getHardNode(equipName));
        String token = JenkinsJobInfo.JobName.JOB9.toString();
        params.put("token", token);
        String jobName = user.getUsername() + "_" + JenkinsConstant.FLARE_EXPORT;
        params.put("jobName", jobName);

        // 开始使用时间
        Date statDate = new Date();
        // 开始build job
        if(jenkins.isJenkinsJobExist(jobName)){
            jenkins.deleteJob(jobName);
        }
        jenkins.createFlareJob(params);
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
            ManpowerDO manpower = new ManpowerDO();
            manpower.setToolId(ToolsConstant.FLARE_ID);
            manpower.setToolName(ToolsConstant.FLARE_NAME);
            manpower.setUseDept(ToolsConstant.hDept);
            manpower.setFunName("开始");
            manpower.setStartUseTime(statDate);
            manpower.setEndUseTime(new Date());
            int minutes = DateUtils.getBetweenMinutes(statDate, new Date());
            DecimalFormat df = new DecimalFormat("0.00");
            manpower.setUseTime(String.valueOf(df.format((float)minutes/60)));
            manpower.setCreateTime(new Date());
            manpowerMapper.save(manpower);
        }
        // 测试结果入库
        LogsDO logDO = GenLogsDO.loadDO(user, ToolsConstant.FLARE_ID, status, ToolsConstant.FLARE_NAME,
                ToolsConstant.FLARE_LOG_PATH);
        logMapper.save(logDO);
        ReportDO reportDO = GenReportDO.loadDO(user, ToolsConstant.FLARE_ID, ToolsConstant.FLARE_NAME,
                ToolsConstant.FLARE_REPORT_PATH);
        reportMapper.save(reportDO);

        return "SUCCESS";
    }

    public boolean cancelTask(String equipName) {
        List<Integer> list = jenkins.getJobBuildListAll(JenkinsConstant.FLARE_EXPORT);
        boolean isFinished = jenkins.isFinished(JenkinsConstant.FLARE_EXPORT, list.get(0));
        if (!isFinished) {
            return jenkins.stopJenkinsJob(JenkinsConstant.FLARE_EXPORT);
        } else {
            return true;
        }
    }
}
