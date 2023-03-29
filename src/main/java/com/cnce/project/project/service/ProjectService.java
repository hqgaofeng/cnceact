package com.cnce.project.project.service;

import com.cnce.project.project.domain.ProjectDO;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public interface ProjectService {
    ProjectDO get(int id);

    List<ProjectDO> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    int save (ProjectDO project);

    int update(ProjectDO project);

    int remove(int id);

    int batchRemove(int[] ids);

    boolean batchImportProjects(List<Map> list);

    List<String> loadProject();
}
