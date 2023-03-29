package com.cnce.manager.manpower.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.utils.PageUtils;
import com.cnce.common.utils.Query;
import com.cnce.manager.manpower.domain.ManPowerVO;
import com.cnce.manager.manpower.domain.ManpowerDO;
import com.cnce.manager.manpower.service.ManpowerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/manager/manPower")
public class ManpowerController {

    private final String prefix = "manager/manPower";

    @Autowired
    private ManpowerService manpowerServic;

    @GetMapping()
    @RequiresPermissions("manager:manPower:manPower")
    String goManpowerShow(Model model) {
        ManPowerVO mp = manpowerServic.getAll();
        model.addAttribute("mp", mp);
        return prefix + "/show";
    }


    @RequiresPermissions("manager:manPower:manPower")
    @GetMapping("/soft")
    String  goSoftToolshow() {
            return prefix + "/softToolshow";
    }


    @RequiresPermissions("manager:manPower:manPower")
    @GetMapping("/hard")
    String goHardToolshow() {
        return prefix + "/hardToolshow";
    }


    @Log("开始下发命令")
    @GetMapping("/softInfo")
    @ResponseBody()
    public PageUtils getsoftInfo(@RequestParam Map<String, Object> params) {
        params.put("useDept", "XA软件测试部");
        Query query = new Query(params);
        List<ManpowerDO> appList = manpowerServic.loadTools(query);
        int total = manpowerServic.count(params);
        PageUtils pageUtil = new PageUtils(appList, total);
        return pageUtil;
    }

    @Log("开始下发命令")
    @GetMapping("/hardInfo")
    @ResponseBody()
    public PageUtils getHardInfo(@RequestParam Map<String, Object> params) {
        params.put("useDept", "XA硬件测试部");
        Query query = new Query(params);
        List<ManpowerDO> appList = manpowerServic.loadTools(query);
        int total = manpowerServic.count(params);
        PageUtils pageUtil = new PageUtils(appList, total);
        return pageUtil;
    }
}
