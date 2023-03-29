package com.cnce.manager.tools.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnce.common.domain.JenkinsJobInfo;
import com.cnce.common.jenkins.JenkinsConstant;
import com.cnce.common.jenkins.JenkinsFilter;
import com.cnce.common.jenkins.JenkinsUtil;
import com.cnce.common.utils.TimeUtils;
import com.cnce.manager.logs.dao.LogsDao;
import com.cnce.manager.manpower.dao.ManpowerDao;
import com.cnce.manager.report.dao.ReportDao;
import com.cnce.manager.tools.service.WifiSignalService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class WifiSignalServiceImpl implements WifiSignalService {

    JenkinsUtil jenkins = new JenkinsUtil();

    private String jobName = JenkinsConstant.WIFI_SIGNAL;

    @Autowired
    private LogsDao logMapper;
    @Autowired
    private ReportDao reportMapper;
    @Autowired
    private ManpowerDao powerMapper;

    @Override
    public boolean beginTest(Map<Object, Object> params) {
        try{
            Map<String,String> sendMap = new HashMap<>();
            sendMap.put("proName", params.get("proName").toString());
            sendMap.put("testStage", params.get("testStage").toString());
            sendMap.put("testPlan", params.get("testPlan").toString());
            sendMap.put("engineer", params.get("engineer").toString());
            sendMap.put("device", params.get("device").toString());
            sendMap.put("beginTime", params.get("beginTime").toString());
            sendMap.put("expPower", params.get("expPower").toString());
            sendMap.put("cbl4Loss", params.get("cbl4Loss").toString());
            sendMap.put("cbl5Loss", params.get("cbl5Loss").toString());
            List<String> sceList = (List<String>) params.get("scene");
            sendMap.put("scenario", StringUtils.join(sceList, ","));
            sendMap.put("labelName", JenkinsFilter.getHardNode("equipName"));
            // sendMap.put("labelName", "test_node_chen");
            String token = JenkinsJobInfo.JobName.JOB11.toString();
            sendMap.put("token", token);
            sendMap.put("jobName", jobName);
            // 组装制式、频段、速率、信道
            List<Map<String, Object>> items = (List<Map<String, Object>>) params.get("items");
            JSONObject jsonObj = new JSONObject();
            List<JSONObject> list = new ArrayList<>();
            for(Map<String, Object> mp: items){
                JSONObject obj = new JSONObject();
                obj.put("id", mp.get("id"));
                obj.put("network", mp.get("network"));
                obj.put("band", mp.get("band"));
                List<String> speed = (List<String>) mp.get("speed");
                obj.put("speed", StringUtils.join(speed, ","));
                List<String> channel = (List<String>) mp.get("channel");
                obj.put("channel", StringUtils.join(channel, ","));
                list.add(obj);
            }
            jsonObj.put("items", list);
            sendMap.put("jsonObj", jsonObj.toString());
            // 开始build job
            jenkins.buildParamJob(jobName, token, sendMap);
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
