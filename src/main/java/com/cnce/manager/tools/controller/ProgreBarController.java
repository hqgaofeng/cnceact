package com.cnce.manager.tools.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.controller.BaseController;
import com.cnce.manager.tools.service.ProgreBarService;
import com.cnce.system.domain.UserDO;
import com.cnce.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/manager/progreBar")
public class ProgreBarController extends BaseController {

    private final String prefix = "manager/progreBar";

    @Autowired
    private ProgreBarService proBarService;
    @Autowired
    private UserService userService;


    @Log("测试任务的执行进度")
    @RequestMapping("/load")
    @ResponseBody()
    public int loadProgress(@RequestParam("toolId") Integer toolId, @RequestParam("timestamp")String date)  {
        UserDO userDO = userService.get(getUserId());
        int value = proBarService.getProgress(toolId, date, userDO);
        return value;
    }

    @Log("查询是否存在测试任务")
    @RequestMapping("/selecTask")
    @ResponseBody()
    boolean buildStart(@RequestParam("toolId")Integer toolId){
        return proBarService.buildStart(toolId);
    }


    @Log("检查执行机节点是否在线")
    @RequestMapping("/checkNodes")
    @ResponseBody()
    boolean loadNodes(){
        UserDO user = userService.get(getUserId());
        return proBarService.checkNodes(user);
    }

}
