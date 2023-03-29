package com.cnce.manager.tools.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.controller.BaseController;
import com.cnce.manager.device.domain.DeviceDO;
import com.cnce.manager.device.service.DeviceService;
import com.cnce.manager.tools.service.EquipService;
import com.cnce.manager.tools.service.ToolsService;
import com.cnce.system.domain.UserDO;
import com.cnce.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/manager")
@Controller
public class EquipController extends BaseController {

    @Autowired
    private EquipService equipService;
    @Autowired
    private ToolsService toolsService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private UserService userService;

    @Log("开始下发命令")
    @GetMapping("/equipName")
    @ResponseBody()
    public List<String> getEquipName(@RequestParam("id")Integer id){
        List<String> devList = new ArrayList<>();
        UserDO userDO = userService.get(getUserId());
        DeviceDO device = deviceService.getDev(userDO);
        if(device != null){
            devList.add(device.getComputerAlias());
            return devList;
        }else {
            Integer deptId = toolsService.getDeptId(id);
            List<String> list = equipService.getEquipName(deptId);
            return list;
        }
    }

    @Log("开始下发命令")
    @GetMapping("/equipIp")
    @ResponseBody()
    public List<String> getEquipIp(@RequestParam("id")Integer id){
        Integer deptId = toolsService.getDeptId(id);
        List<String> list = equipService.getEquipIp(deptId);
        return list;
    }
}
