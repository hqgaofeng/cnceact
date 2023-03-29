package com.cnce.project.manpower.service;

import com.cnce.project.manpower.domain.ProjectUserDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ProjectUserService {

    ProjectUserDO get(String id);

    List<ProjectUserDO> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    int update(ProjectUserDO projectUser);

    int save(ProjectUserDO projectUser);

    List<ProjectUserDO> getDepartment(String region);

    List<ProjectUserDO> getDomain(String region, String department);
}
