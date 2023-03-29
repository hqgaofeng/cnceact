package com.cnce.project.project.controller;

import com.cnce.common.utils.PageUtils;
import com.cnce.common.utils.Query;
import com.cnce.project.project.domain.ProjectPowerDetailDO;
import com.cnce.project.project.service.ProjectPowerDetailService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RequestMapping("/project/powerDetail")
@Controller
public class ProjectPowerDetailController {

    private final String prefix = "project/powerDetail";

    @Autowired
    private ProjectPowerDetailService powerDetailService;


    @RequiresPermissions("project:powerDetail:powerDetail")
    @GetMapping()
    public String template(Model model) {
        return prefix + "/powerDetail";
    }

    @GetMapping("/list")
    @ResponseBody()
    public PageUtils list(@RequestParam Map<String, Object> params){
        Query query = new Query(params);
        List<ProjectPowerDetailDO> project = powerDetailService.list(query);
        int total = powerDetailService.count(query);
        PageUtils pageUtil = new PageUtils(project, total);
        return pageUtil;
    }

}
