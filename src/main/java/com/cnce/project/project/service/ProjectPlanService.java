package com.cnce.project.project.service;

import com.cnce.project.project.domain.ProjectPlanDO;
import com.cnce.system.domain.UserDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ProjectPlanService {
    ProjectPlanDO get(int id);

    List<ProjectPlanDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(ProjectPlanDO plan, UserDO user);

    int update(ProjectPlanDO plan, UserDO user);

    int remove(int id);

    int batchRemove(int[] ids);

    ProjectPlanDO getProjectByPlan(@Param("planName") String planName);

    List<String> loadProjectPlan();

}
