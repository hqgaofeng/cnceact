package com.cnce.project.project.service.impl;

import com.cnce.common.utils.DateUtils;
import com.cnce.project.project.dao.ProjectDao;
import com.cnce.project.project.dao.ProjectPlanDao;
import com.cnce.project.project.domain.ProjectDO;
import com.cnce.project.project.domain.ProjectPlanDO;
import com.cnce.project.project.service.ProjectPlanService;
import com.cnce.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;


@Service
public class ProjectPlanServiceImpl implements ProjectPlanService {

    SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ProjectDao projectMapper;
    @Autowired
    private ProjectPlanDao projectPlanMapper;


    @Override
    public ProjectPlanDO get(int id) {
        return projectPlanMapper.get(id);
    }

    @Override
    public List<ProjectPlanDO> list(Map<String, Object> map) {
        return projectPlanMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return projectPlanMapper.count(map);
    }

    @Override
    public int save(ProjectPlanDO plan, UserDO user) {
        String staTime = plan.getPlanStartTime().toString();
        String endTime = plan.getPlanEndTime().toString();
        ProjectDO pd = projectMapper.getProjectByName(plan.getpName());
        if(pd != null){
            plan.setpId(pd.getpId());
        }
        try {
            Date da1 = sdf1.parse(staTime);
            Date da2 = sdf1.parse(endTime);
            int testCycle = DateUtils.getBetweenDays(sdf2.format(da1), sdf2.format(da2));
            plan.setTestCycle(testCycle + 1);
            plan.setWritten(user.getName());
            plan.setCreateTime(new Date());
        }catch (Exception e){
            e.printStackTrace();
        }
        return projectPlanMapper.save(plan);
    }

    @Override
    public int update(ProjectPlanDO plan, UserDO user) {
        String startTime = plan.getPlanStartTime().toString();
        String endTime = plan.getPlanEndTime().toString();
        ProjectDO pd = projectMapper.getProjectByName(plan.getpName());
        if(pd != null){
            plan.setpId(pd.getpId());
        }
       try {
           Date date1 = sdf1.parse(startTime);
           Date date2 = sdf1.parse(endTime);
           int testCycle = DateUtils.getBetweenDays(sdf2.format(date1), sdf2.format(date2));
           plan.setTestCycle(testCycle + 1);
           plan.setWritten(user.getName());
           plan.setCreateTime(new Date());
       }catch (Exception e){
           e.printStackTrace();
       }
        return projectPlanMapper.update(plan);
    }

    @Override
    public int remove(int id) {
        return projectPlanMapper.remove(id);
    }

    @Override
    public int batchRemove(int[] ids) {
        return projectPlanMapper.batchRemove(ids);
    }

    @Override
    public ProjectPlanDO getProjectByPlan(String planName) {
        ProjectPlanDO projectPlan = projectPlanMapper.getProjectByPlan(planName);
        return projectPlan;
    }

    @Override
    public List<String> loadProjectPlan() {
        return projectPlanMapper.loadProjectPlan();
    }
}