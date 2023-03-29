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
import com.cnce.manager.tools.dao.CameraStressTestingDao;
import com.cnce.manager.tools.domain.CameraStressTestingDO;
import com.cnce.manager.tools.service.CameraStressTestingService;
import com.cnce.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;


@Service
public class CameraStressTestingServiceImpl implements CameraStressTestingService {

    JenkinsUtil jenkins = new JenkinsUtil();
    private String jobName = JenkinsConstant.CMA_STRENGTH;
    private String token = JenkinsJobInfo.JobName.JOB3.toString();
    @Autowired
    private CameraStressTestingDao cameraStressMapper;
    @Autowired
    private LogsDao logMapper;
    @Autowired
    private ReportDao reportMapper;
    @Autowired
    private ManpowerDao manpowerMapper;

    @Override
    public List<String> getModelNameByModelSort(String modelSort) {
        List<String> resList = new ArrayList<>();
        List<CameraStressTestingDO> list = cameraStressMapper.getModelNameByModelSort(modelSort);
        for (int i = 0; i < list.size(); i++) {
            String value = list.get(i).getModelName();
            resList.add(value);
        }
        return resList;
    }


    @Override
    public List<String> getResoNameByModelSort(String modelSort) {
        List<String> resList = new ArrayList<>();
        List<CameraStressTestingDO> list = cameraStressMapper.getResoNameByModelSort(modelSort);
        for (int i = 0; i < list.size(); i++) {
            String value = list.get(i).getResoName();
            resList.add(value);
        }
        return resList;
    }

    public String startTask(Map<String, String> params, UserDO user) {
        if("4:3(推荐)".equals(params.get("cameraResolution"))){
            params.put("cameraResolution", "4:3");
        }else if("20:9 1080p".equals( params.get("cameraResolution"))){
            params.put("cameraResolution", "20:9(1080p)");
        }else if("16:9 1080p(推荐)".equals( params.get("cameraResolution"))) {
            params.put("cameraResolution", "16:9(1080p)");
        }else if("16:9 720p".equals( params.get("cameraResolution"))) {
            params.put("cameraResolution", "16:9(720p)");
        }
        params.put("labelName", JenkinsFilter.getSoftNode(params.get("equipName").toString()));

        // 开始使用时间
        Date statDate = new Date();
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

        // 检查测试结果是否有效
        List<Integer> bs = jenkins.getJobBuildListAll(jobName);
        String result = jenkins.getBuildResult(bs.get(0), jobName);
        Integer status = JenkinsFilter.getResCode(result);
        if(status == 1){
            // 工具的人力数据入库
            List<ManpowerDO> manList = new ArrayList<>();
            for(int i=0; i<6; i++){
                ManpowerDO manpower = new ManpowerDO();
                manpower.setToolId(ToolsConstant.CMASTR_ID);
                manpower.setToolName(ToolsConstant.CMASTR_NAME);
                manpower.setUseDept(ToolsConstant.sDept);
                manpower.setStartUseTime(statDate);
                manpower.setEndUseTime(new Date());
                int minutes = DateUtils.getBetweenMinutes(statDate, new Date());
                DecimalFormat df = new DecimalFormat("0.00");
                manpower.setUseTime(String.valueOf(df.format((float)minutes/60)));
                manpower.setCreateTime(new Date());
                manpower.setFunName("功能选择");
                manList.add(manpower);
                manpower.setFunName("测试镜头");
                manList.add(manpower);
                manpower.setFunName("相机模式");
                manList.add(manpower);
                manpower.setFunName("分辨率");
                manList.add(manpower);
                manpower.setFunName("测试次数");
                manList.add(manpower);
                manpower.setFunName("开始");
                manList.add(manpower);
            }
            manpowerMapper.insertList(manList);
        }
        // 测试结果入库
        LogsDO logDO = GenLogsDO.loadDO(user, ToolsConstant.CMASTR_ID, status, ToolsConstant.CMASTR_NAME,
                ToolsConstant.CMASTRE_LOG_PATH);
        logMapper.save(logDO);
        ReportDO reportDO = GenReportDO.loadDO(user, ToolsConstant.CMASTR_ID, ToolsConstant.CMASTR_NAME,
                ToolsConstant.CMASTRE_REPORT_PATH);
        reportMapper.save(reportDO);
        return "SUCCESS";
    }

}
