package com.cnce.manager.tools.controller;

import com.cnce.common.controller.BaseController;
import com.cnce.manager.tools.domain.WifiDO;
import com.cnce.manager.tools.service.WifiService;
import com.cnce.system.domain.UserDO;
import com.cnce.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/manager/wifi")
public class WifiController extends BaseController {

    private final String prefix = "manager/wifi";

    @Autowired
    private UserService userService;
    @Autowired
    private WifiService wifiService;


    @GetMapping()
    @RequiresPermissions("manager:wifi:wifi")
    String goWiFi() {
        return prefix + "/wifi";
    }


    @RequestMapping("/start")
    @ResponseBody
    public Boolean startTask(@RequestBody Map<Object, Object> params) {
        WifiDO wifiDO = new WifiDO();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-ddd");
        wifiDO.setProjectName(params.get("projectName").toString());
        wifiDO.setTestStage(params.get("projectStage").toString());
        wifiDO.setTestPlan(params.get("projectScheme").toString());
        wifiDO.setEngineer(params.get("engineer").toString());
        wifiDO.setDevice(params.get("equipment").toString());
        try {
            wifiDO.setTestTime(formatter.parse(params.get("testTime").toString()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        wifiDO.setInitLevel(params.get("levelValue").toString());
        wifiDO.setPower(params.get("power").toString());
        wifiDO.setLineLoss(params.get("lineLoss").toString());
        wifiDO.setScenario(params.get("scenario").toString());
        wifiDO.setNetwork(params.get("internet").toString());
        wifiDO.setSpectrum(params.get("band").toString());
        wifiDO.setChannel(params.get("channel").toString());
        wifiDO.setEquipName(params.get("selectEquip").toString());
        UserDO user = userService.get(getUserId());
        boolean start = wifiService.beginTask(wifiDO);
        if (start) {
            wifiService.saveLogs(user);
            wifiService.saveResult(user);
        }
        return start;
    }
}
