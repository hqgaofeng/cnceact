package com.cnce.project.project.controller;

import com.cnce.common.utils.PageUtils;
import com.cnce.common.utils.Query;
import com.cnce.project.project.domain.ProjectPowerDO;
import com.cnce.project.project.service.ProjectPowerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RequestMapping("/project/manpower")
@Controller
public class ProjectPowerController {

    private final String prefix = "project/propower";

    @Autowired
    private ProjectPowerService projectPowerService;

    @RequiresPermissions("project:manpower:manpower")
    @GetMapping()
    public String projectPower(Model model) {
        return prefix + "/manpower";
    }

    @GetMapping("/list")
    @ResponseBody()
    public PageUtils list(@RequestParam Map<String, Object> params){
        Query query = new Query(params);
        List<ProjectPowerDO> project = projectPowerService.list(query);
        int total = projectPowerService.count(query);
        PageUtils pageUtil = new PageUtils(project, total);
        return pageUtil;
    }

    @GetMapping("/loadTotalPower")
    @ResponseBody()
    public String loadTotalPower(){
        String totalPower = projectPowerService.loadTotalPower();
        return totalPower;
    }
}
