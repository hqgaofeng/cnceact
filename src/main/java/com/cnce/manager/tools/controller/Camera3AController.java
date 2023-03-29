package com.cnce.manager.tools.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.controller.BaseController;
import com.cnce.manager.tools.service.Camera3AService;
import com.cnce.system.domain.UserDO;
import com.cnce.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 工具管理
 *
 * @author Alan
 * @email chenfei917@163.com
 * @date 2017-09-27 14:40:36
 */

@Controller
@RequestMapping("/manager/camera3ATools")
public class Camera3AController extends BaseController {

    private final String prefix = "manager/camera3ATools";

    @Autowired
    private Camera3AService camera3AService;
    @Autowired
    private UserService userService;

    @GetMapping()
    @RequiresPermissions("manager:camera3ATools:camera3A")
    String goPage(){
        return prefix + "/camera3A";
    }

    @Log("开始下发命令")
    @RequestMapping("/start")
    @ResponseBody()
    public String startTask(@RequestBody Map<String, String> params){
        UserDO userDO = userService.get(getUserId());
        String result = camera3AService.startTask(params, userDO);
        return result;
    }

    @Log("开始下发命令")
    @RequestMapping("/cancel")
    @ResponseBody()
    public boolean cancelTask(String equipName){
        boolean canceled = camera3AService.cancelTask(equipName);
        return canceled;
    }
}
