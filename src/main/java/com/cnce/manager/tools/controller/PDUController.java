package com.cnce.manager.tools.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.controller.BaseController;
import com.cnce.manager.tools.service.PDUService;
import com.cnce.system.domain.UserDO;
import com.cnce.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


@Controller
@RequestMapping("/manager/pwOnOff")
public class PDUController extends BaseController {

    private final String prefix = "manager/pwOnOff";

    @Autowired
    private UserService userService;
    @Autowired
    private PDUService pduService;


    @GetMapping()
    @RequiresPermissions("manager:pwOnOff:pwOnOff")
    String goPage() {
        return prefix + "/pwOnOff";
    }


    @RequestMapping("/beginTask")
    @ResponseBody
    public Boolean startTask(@RequestBody Map<String, String> params) {
        UserDO user = userService.get(getUserId());
        boolean finish = pduService.beginTask(params);
        if(finish){
            pduService.saveLogs(user);
            pduService.saveResult(user);
        }
        return finish;
    }

    @Log("开始下发命令")
    @RequestMapping("/cancel")
    @ResponseBody()
    public boolean cancelTask(String devName) {
        return pduService.cancelTask(devName);
    }
}
