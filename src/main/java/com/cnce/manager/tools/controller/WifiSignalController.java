package com.cnce.manager.tools.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.controller.BaseController;
import com.cnce.manager.tools.service.WifiSignalService;
import com.cnce.system.domain.UserDO;
import com.cnce.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/manager/wifiSignal")
public class WifiSignalController extends BaseController {

    private final String prefix = "manager/wifiSignal";

    @Autowired
    private WifiSignalService wifiService;
    @Autowired
    private UserService userService;

    @GetMapping()
    @RequiresPermissions("manager:wifiSignal:wifiSignal")
    String goPage() {
        return prefix + "/wifiSignal";
    }

    @Log("开始测试")
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ResponseBody
    public boolean beginTest(@RequestBody Map<Object, Object> params){
        UserDO userDO = userService.get(getUserId());
        boolean send = wifiService.beginTest(params);
        return send;
    }

    @Log("取消测试")
    @RequestMapping("/cancel")
    @ResponseBody
    public boolean cancel(String equipName){
        boolean canceled = wifiService.cancelTask(equipName);
        return canceled;
    }
}
