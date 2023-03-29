package com.cnce.project.manpower.service.impl;

import com.cnce.project.manpower.dao.ProjectDeptDao;
import com.cnce.project.manpower.domain.ProjectDeptDO;
import com.cnce.project.manpower.service.ProjectDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectDeptServiceImpl implements ProjectDeptService {

    @Autowired
    private ProjectDeptDao  deptMapper;


    @Override
    public List<ProjectDeptDO> getDeptName() {
        return deptMapper.getDeptName();
    }

    @Override
    public List<ProjectDeptDO> loadDepartment(int deptId) {
        return deptMapper.loadDepartment(deptId);
    }

    @Override
    public List<ProjectDeptDO> getDept(String deptName) {
        return deptMapper.getDept(deptName);
    }

    @Override
    public List<ProjectDeptDO> getDept2(ProjectDeptDO projectDeptDO) {
        return deptMapper.getDept2(projectDeptDO);
    }
}
