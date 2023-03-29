package com.cnce.manager.tools.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.controller.BaseController;
import com.cnce.manager.tools.service.CameraService;
import com.cnce.manager.tools.service.FlareService;
import com.cnce.system.domain.UserDO;
import com.cnce.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

/**
 * 工具管理
 *
 * @author Alan
 * @email chenfei917@163.com
 * @date 2017-09-27 14:40:36
 */

@Controller
@RequestMapping("/manager/flare")
public class FlareController extends BaseController {

    private final String prefix = "manager/flare";

    @Autowired
    private FlareService flareService;
    @Autowired
    private UserService userService;

    @GetMapping()
    @RequiresPermissions("manager:flare:flare")
    String upgradeTools() {
        return prefix + "/flare";
    }

    @Log("开始下发命令")
    @RequestMapping("/start")
    @ResponseBody()
    public String startTask(@RequestParam("projectName") String projectName, @RequestParam("cameraLens") String cameraLens,
                            @RequestParam("testScene") String testScene, @RequestParam("path") String path,
                            @RequestParam("name") String name,@RequestParam("number") String number, @RequestParam("equipName") String equipName) {
        UserDO user = userService.get(getUserId());
        String result=flareService.startTask(projectName, cameraLens,testScene, path, name,number, equipName, user);
        return result;
    }

    @Log("开始下发命令")
    @RequestMapping("/cancel")
    @ResponseBody()
    public boolean cancelTask(String equipName) {
        return flareService.cancelTask(equipName);
    }

}
