package com.cnce.manager.tools.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.controller.BaseController;
import com.cnce.manager.tools.service.ToolsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 工具管理
 *
 * @author Alan
 * @email chenfei917@163.com
 * @date 2017-09-27 14:40:36
 */

@Controller
@RequestMapping("/manager/tools")
public class ToolsController extends BaseController {

    private final String prefix = "manager/tools";

    @Autowired
    private ToolsService toolsService;

    @GetMapping()
    @RequiresPermissions("manager:tools:tools")
    String tools() {
        return prefix + "/show";
    }


    @RequestMapping("/camera3A")
    public String toCamera3A() {
        return "/manager/camera3ATools";
    }

    @Log("开始下发命令")
    @RequestMapping("/toolName")
    @ResponseBody()
    public List<String> getToolName(){
        return toolsService.getToolsName();
    }


    @Log("开始下发命令")
    @GetMapping("/download")
    @ResponseBody()
    public void downloadFile(String  fileName, HttpServletResponse response) {
        toolsService.downFile(fileName,response);
    }

}
