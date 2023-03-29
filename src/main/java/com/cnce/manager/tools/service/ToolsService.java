package com.cnce.manager.tools.service;

import com.cnce.manager.tools.domain.ToolsDO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 工具管理
 * 
 * @author Alan
 * @email chenfei917@163.com
 * @date 2017-09-27 14:28:36
 */
public interface ToolsService {

    ToolsDO get(Integer id);

    Integer getDeptId(Integer id);

    List<String> getToolsName();

    void  downFile(String  fileName, HttpServletResponse response);

    Integer getToolIdByName(String toolName);

}
