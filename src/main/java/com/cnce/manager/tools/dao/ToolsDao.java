package com.cnce.manager.tools.dao;

import com.cnce.manager.tools.domain.ToolsDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ToolsDao {

    ToolsDO get(Integer id);

    ToolsDO getToolsDeptIdById(Integer id);

    List<ToolsDO> getToolsName();

    List<ToolsDO> getToolId(Integer deptId);

    List<ToolsDO> getToolsByDept(String deptName);

    Integer getToolIdByName(String toolName);

}
