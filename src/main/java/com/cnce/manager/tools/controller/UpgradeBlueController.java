package com.cnce.manager.tools.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.controller.BaseController;
import com.cnce.manager.tools.service.UpgradeBlueService;
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
@RequestMapping("/manager/upgradeBlueTools")
public class UpgradeBlueController extends BaseController {

    private final String prefix = "manager/upgradeBlueTools";

    @Autowired
    private UpgradeBlueService upgradeBlueService;
    @Autowired
    private UserService userService;

    @GetMapping()
    @RequiresPermissions("manager:upgradeBlueTools:upgradeBlue")
    String upgradeBlueTools() {
        return prefix + "/upgradeBlue";
    }

    @Log("开始下发命令")
    @RequestMapping("/downLoad")
    @ResponseBody()
    public String downLoad(@RequestParam("localPath") String localPath, @RequestParam("remotePath") String remotePath,
                           @RequestParam("packType") String packType,@RequestParam("equipName") String equipName) {

        UserDO user = userService.get(getUserId());
        String result=upgradeBlueService.downLoad(localPath, remotePath, packType, equipName, user);
        return result;
    }


    @Log("开始下发命令")
    @RequestMapping("/onlyUpgradeBlue")
    @ResponseBody()
    public String onlyUpgrade(@RequestParam( "localPath") String localPath, @RequestParam("platformType") String platformType,
                              @RequestParam("packType") String packType,@RequestParam("equipName") String equipName) {

        UserDO user = userService.get(getUserId());
        String result=upgradeBlueService.onlyUpgrade(localPath, platformType, packType, equipName, user);
        return result;

    }

    @Log("开始下发命令")
    @RequestMapping("/upgradeAndRootBlue")
    @ResponseBody()
    public String upgradeAndRoot(@RequestParam("localPath") String localPath, @RequestParam("platformType") String platformType,
                                 @RequestParam("packType") String packType,@RequestParam("equipName") String equipName) {

        UserDO user = userService.get(getUserId());
        String result=upgradeBlueService.upgradeAndRoot(localPath, platformType, packType, equipName, user);
        return result;

    }

    @Log("开始下发命令")
    @RequestMapping("skipWizard")
    @ResponseBody()
    public String skipWizad(String equipName) {
        UserDO user = userService.get(getUserId());
        String result=upgradeBlueService.skipWizad(equipName, user);
        return result;

    }

    @Log("开始下发命令")
    @RequestMapping("fullProcessBlue")
    @ResponseBody()
    public String fullProcess(@RequestParam("localPath") String localPath, @RequestParam("remotePath") String remotePath,
                              @RequestParam("platformType") String platformType, @RequestParam("packType") String packType,
                              @RequestParam("equipName") String equipName) {

        UserDO user = userService.get(getUserId());
        String result=upgradeBlueService.fullProcess(localPath, remotePath, platformType, packType, equipName, user);
        return result;

    }
}
