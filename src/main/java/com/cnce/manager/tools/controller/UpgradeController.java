package com.cnce.manager.tools.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.controller.BaseController;
import com.cnce.manager.tools.service.UpgradeService;
import com.cnce.system.domain.UserDO;
import com.cnce.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 工具管理
 *
 * @author Alan
 * @email chenfei917@163.com
 * @date 2017-09-27 14:40:36
 */

@Controller
@RequestMapping("/manager/upgradeTools")
public class UpgradeController extends BaseController {

    private final String prefix = "manager/upgradeTools";

    @Autowired
    private UpgradeService upgradeService;
    @Autowired
    private UserService userService;

    @GetMapping()
    @RequiresPermissions("manager:upgradeTools:upgrade")
    String upgradeTools() {
        return prefix + "/upgrade";
    }

    @Log("开始下发命令")
    @RequestMapping("/onlyUpgrade")
    @ResponseBody()
    public String onlyUpgrade(@RequestParam("localPath") String localPath, @RequestParam("platformType") String platformType,
                              @RequestParam( "packType") String packType,@RequestParam( "equipName") String equipName) throws InterruptedException {
        UserDO user = userService.get(getUserId());
        String result=upgradeService.onlyUpgrade(localPath, platformType, packType, equipName, user);
        return result;

    }

    @Log("开始下发命令")
    @RequestMapping("/upgradeAndRoot")
    @ResponseBody()
    public String upgradeAndRoot(@RequestParam("localPath") String localPath, @RequestParam("platformType") String platformType,
                                 @RequestParam("packType") String packType,@RequestParam( "equipName") String equipName) {

        UserDO user = userService.get(getUserId());
        String result=upgradeService.upgradeAndRoot(localPath, platformType, packType, equipName, user);
        return result;

    }

    @Log("开始下发命令")
    @RequestMapping("/skipWizard")
    @ResponseBody()
    public String skipWizad(@RequestParam( "equipName") String equipName) {
        UserDO user = userService.get(getUserId());
        String result=upgradeService.skipWizad(equipName, user);
        return result;

    }


}
