package com.cnce.manager.tools.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.controller.BaseController;
import com.cnce.common.utils.PageUtils;
import com.cnce.common.utils.Query;
import com.cnce.manager.tools.domain.HeartbeatAndPretestBlueDO;
import com.cnce.manager.tools.domain.TopAPPDO;
import com.cnce.manager.tools.service.HeartbeatAndPretestBlueService;
import com.cnce.system.domain.UserDO;
import com.cnce.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 工具管理
 *
 * @author Alan
 * @email chenfei917@163.com
 * @date 2017-09-27 14:40:36
 */

@Controller
@RequestMapping("/manager/heartbeatAndPretestBlueTools")
public class HeartbeatAndPretestBlueController extends BaseController {

    private final String prefix = "manager/heartbeatAndPretestBlueTools";

    @Autowired
    private HeartbeatAndPretestBlueService heartbeatAndPretestBlueService;
    @Autowired
    private UserService userService;

    @GetMapping()
    @RequiresPermissions("manager:heartbeatAndPretestBlueTools:heartbeatAndPretestBlue")
    String tools() {
        return prefix + "/heartbeatAndPretestBlue";
    }


    @Log("开始下发命令")
    @GetMapping("/heartbeatCaseInfo")
    @ResponseBody()
    public PageUtils getAllAppInfo(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<HeartbeatAndPretestBlueDO> appList = heartbeatAndPretestBlueService.getAllAppInfo(query);

        int total = heartbeatAndPretestBlueService.count(query);
        PageUtils pageUtil = new PageUtils(appList, total);
        return pageUtil;
    }

    @Log("开始下发命令")
    @GetMapping("/startTest")
    @ResponseBody
    public String startTest(@RequestParam("names[]") String[] names, @RequestParam("num") String num,
                            @RequestParam("equipName") String equipName) {
        UserDO userDO = userService.get(getUserId());
        String result=heartbeatAndPretestBlueService.startTest(names, Integer.parseInt(num), equipName, userDO);
        return result;

    }

    @Log("开始下发命令")
    @PostMapping("cancelTest")
    @ResponseBody
    public String cancelTest(@RequestParam("flag") boolean flag,@RequestParam("equipName") String equipName) {
        boolean cancel = heartbeatAndPretestBlueService.cancelTest(flag,equipName);
        return prefix + "/heartbeatAndPretestBlue";
    }

}
