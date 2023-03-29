package com.cnce.project.manpower.dao;

import com.cnce.project.manpower.domain.ProjectUserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProjectUserDao {

    ProjectUserDO get(String id);

    List<ProjectUserDO> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    ProjectUserDO getUserByJobNo(@Param("jobNo") int jobNo);

    int update(ProjectUserDO projectUser);

    int save(ProjectUserDO projectUser);

    void saveUsers(@Param("list") List<ProjectUserDO> list);

    List<ProjectUserDO> getDepartment(@Param("region") String region);

    List<ProjectUserDO> getDomain(@Param("region") String region, @Param("department") String department);
}
