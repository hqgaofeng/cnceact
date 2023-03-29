package com.cnce.project.manpower.service;

import com.cnce.project.manpower.domain.ProjectDeptDO;

import java.util.List;


public interface ProjectDeptService {

    List<ProjectDeptDO> getDeptName();

    List<ProjectDeptDO> getDept(String deptName);

    List<ProjectDeptDO> loadDepartment(int deptId);

    List<ProjectDeptDO> getDept2(ProjectDeptDO projectDeptDO);


}
