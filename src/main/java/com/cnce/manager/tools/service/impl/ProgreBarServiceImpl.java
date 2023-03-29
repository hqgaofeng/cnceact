package com.cnce.manager.tools.service.impl;


import com.cnce.common.domain.ToolsInfo;
import com.cnce.common.jenkins.JenkinsFilter;
import com.cnce.common.jenkins.JenkinsUtil;
import com.cnce.manager.tools.service.ProgreBarService;
import com.cnce.system.domain.UserDO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Service
public class ProgreBarServiceImpl implements ProgreBarService {

    JenkinsUtil jenkins = new JenkinsUtil();

    @Override
    public boolean buildStart(Integer toolId) {
        String jobName = JenkinsFilter.getjobName(toolId);
        List<Integer> list = jenkins.getJobBuildListAll(jobName);
        boolean isEnd = jenkins.isFinished(jobName, list.get(0));
        if (!isEnd) {
            System.out.println("执行机正在构建任务，需排队等候");
            return false;
        }
        return true;
    }

    @Override
    public int getProgress(Integer toolId, String date, UserDO user) {
        String jobName = null;
        if(toolId==4 || toolId==5 || toolId==6 || toolId==7 || toolId==8
                || toolId==10 || toolId==11 || toolId==12){
            jobName = JenkinsFilter.getjobName(toolId);
        }else {
            jobName = user.getUsername() + "_" + JenkinsFilter.getjobName(toolId);
        }
        // 检查job是否build结束
        List<Integer> builds = jenkins.getJobBuildListAll(jobName);
        boolean finish = jenkins.isFinished(jobName, builds.get(0));
        if(jenkins.isFinished(jobName, builds.get(0))){
            return 100;
        }else {
            // 当前时间
            Date currTime = new Date();
            BigDecimal cuTime = new BigDecimal(currTime.getTime());
            // job开始时间
            BigDecimal staTine = new BigDecimal(date);
            // job耗时
            int jobEstimated = JenkinsFilter.getEstimated(toolId);
            BigDecimal estim = BigDecimal.valueOf((int)jobEstimated);
            // 当前进度
            BigDecimal subtract = cuTime.subtract(staTine);
            BigDecimal divide = subtract.divide(estim, 2,BigDecimal.ROUND_HALF_UP);
            BigDecimal result = divide.multiply(new BigDecimal("100"));
            if(result.intValue() >= 100){
                return 99;
            }
            return result.intValue();
        }
    }

    @Override
    public boolean checkNodes(UserDO user) {
        // 检查是否是普通测试用户，是测试用户再检查节点是否在线
        String labelName = JenkinsFilter.getNodes(user.getUsername());
        if(!"".equals(labelName)){
            return jenkins.checkNodeOnline(labelName);
        }
        return true;
    }

}