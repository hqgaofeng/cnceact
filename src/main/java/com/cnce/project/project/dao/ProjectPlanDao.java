package com.cnce.project.project.dao;

import com.cnce.project.project.domain.ProjectPlanDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProjectPlanDao {

    ProjectPlanDO get(int id);

    List<ProjectPlanDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(ProjectPlanDO plan);

    int update(ProjectPlanDO plan);

    int remove(int id);

    int batchRemove(int[] ids);

    ProjectPlanDO getProjectByPlan(@Param("planName") String planName);

    List<String> loadProjectPlan();

}

