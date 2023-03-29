package com.cnce.project.project.dao;

import com.cnce.project.project.domain.ProjectDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProjectDao {

    ProjectDO get(int id);

    ProjectDO getProjectByName(@Param("proName") String proName);

    List<ProjectDO> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    int save (ProjectDO project);

    int update(ProjectDO project);

    int remove(int id);

    int batchRemove(int[] ids);

    boolean batchImportProjects(@Param("list") List<ProjectDO> list);

    List<String> loadProject();

}

