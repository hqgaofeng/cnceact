package com.cnce.manager.tools.controller;

import com.cnce.common.controller.BaseController;
import com.cnce.manager.tools.service.MachineService;
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
@RequestMapping("/manager/machine")
public class MachineController extends BaseController {

    private final String prefix = "manager/machine";

    @Autowired
    private UserService userService;
    @Autowired
    private MachineService machineService;


    @GetMapping()
    @RequiresPermissions("manager:machine:machine")
    String goPage() {
        return prefix + "/machine";
    }

    @RequestMapping("/start")
    @ResponseBody
    public boolean beginTask(@RequestBody Map<String, String> params) {
        UserDO user = userService.get(getUserId());
        boolean hasRun = machineService.beginTask(params);
        if (hasRun) {
            machineService.saveLogs(user);
            machineService.saveResult(user);
        }
        return true;
    }
}
