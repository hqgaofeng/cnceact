package com.cnce.manager.tools.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.controller.BaseController;
import com.cnce.common.utils.PageUtils;
import com.cnce.common.utils.Query;
import com.cnce.manager.tools.domain.HeartbeatAndPretestBlueDO;
import com.cnce.manager.tools.service.HeartbeatAndPretestService;
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
@RequestMapping("/manager/heartbeatAndPretestTools")
public class HeartbeatAndPretestController extends BaseController {

    private final String prefix = "manager/heartbeatAndPretestTools";

    @Autowired
    private HeartbeatAndPretestService heartbeatAndPretestService;
    @Autowired
    private UserService userService;

    @GetMapping()
    @RequiresPermissions("manager:heartbeatAndPretestTools:heartbeatAndPretest")
    String tools() {
        return prefix + "/heartbeatAndPretest";
    }

    @Log("开始下发命令")
    @GetMapping("/heartbeatCaseInfo")
    @ResponseBody()
    public PageUtils getAllAppInfo(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<HeartbeatAndPretestBlueDO> appList = heartbeatAndPretestService.getAllAppInfo(query);

        int total = heartbeatAndPretestService.count(query);
        PageUtils pageUtil = new PageUtils(appList, total);
        return pageUtil;
    }

    @Log("开始下发命令")
    @PostMapping("startTest")
    @ResponseBody
    public String startTest(@RequestParam("names[]") String[] names, @RequestParam("num") int num,
                            @RequestParam("equipName")String equipName) {
        UserDO userDO = userService.get(getUserId());
        heartbeatAndPretestService.startTest(names, num, equipName, userDO);
        return prefix + "/heartbeatAndPretest";

    }

    @Log("开始下发命令")
    @PostMapping("cancelTest")
    @ResponseBody
    public String cancelTest(@RequestParam("flag") boolean flag,@RequestParam("equipName")String equipName) {
        boolean cancel = heartbeatAndPretestService.cancelTest(flag,equipName);
        return prefix + "/heartbeatAndPretest";
    }

}
