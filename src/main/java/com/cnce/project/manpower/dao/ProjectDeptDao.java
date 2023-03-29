package com.cnce.project.manpower.dao;

import com.cnce.project.manpower.domain.ProjectDeptDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProjectDeptDao {

    List<ProjectDeptDO> list(Map<String, Object> map);
    // 获取部门层级关系
    List<ProjectDeptDO> getDeptName();

    List<ProjectDeptDO> loadDepartment(int deptId);

    List<ProjectDeptDO> getDept(String deptName);

    List<ProjectDeptDO> getDept2(ProjectDeptDO projectDeptDO);

    List<ProjectDeptDO> getUpDept(int deptId);

    int save(ProjectDeptDO projectDeptDO);

    boolean batchSave(List<ProjectDeptDO> list);

    Integer getRootDeptId(int deptId);
}
