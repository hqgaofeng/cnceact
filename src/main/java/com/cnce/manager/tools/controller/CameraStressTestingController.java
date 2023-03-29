package com.cnce.manager.tools.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.controller.BaseController;
import com.cnce.manager.tools.service.CameraStressTestingService;
import com.cnce.system.domain.UserDO;
import com.cnce.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 工具管理
 *
 * @author Alan
 * @email chenfei917@163.com
 * @date 2017-09-27 14:40:36
 */

@Controller
@RequestMapping("/manager/cameraStressTools")
public class CameraStressTestingController extends BaseController {

    private final String prefix = "manager/cameraStressTools";

    @Autowired
    private CameraStressTestingService cameraStressTestingService;
    @Autowired
    private UserService userService;

    @GetMapping()
    @RequiresPermissions("manager:cameraStressTools:cameraStress")
    String tools() {
        return prefix + "/cameraStress";
    }

    @Log("开始下发命令")
    @GetMapping("/modelName")
    @ResponseBody()
    public List<String> getModelNameByModelSort(@RequestParam("modelSort") String modelSort) {
        List<String> list = cameraStressTestingService.getModelNameByModelSort(modelSort);
        return list;
    }

    @Log("开始下发命令")
    @GetMapping("/resoName")
    @ResponseBody()
    public List<String> getResoNameByModelSort(@RequestParam("modelSort") String modelSort) {
        List<String> list = cameraStressTestingService.getResoNameByModelSort(modelSort);
        return list;
    }

    @Log("开始下发命令")
    @PostMapping("/startTask")
    @ResponseBody()
    public String startTask(@RequestBody Map<String, String> params){
        UserDO userDO = userService.get(getUserId());
        String result=cameraStressTestingService.startTask(params, userDO);
        return result;
    }


}
