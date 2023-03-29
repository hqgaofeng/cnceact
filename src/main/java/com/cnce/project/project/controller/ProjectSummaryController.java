package com.cnce.project.project.controller;

import com.cnce.common.utils.PageUtils;
import com.cnce.common.utils.Query;
import com.cnce.project.project.domain.ProjectSummaryDO;
import com.cnce.project.project.service.ProjectSummaryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RequestMapping("/project/summary")
@Controller
public class ProjectSummaryController {

    private final String prefix = "project/summary";

    @Autowired
    private ProjectSummaryService projectSummaryService;

    @RequiresPermissions("project:summary:summary")
    @GetMapping()
    public String template(Model model) {
        return prefix + "/summary";
    }

    @GetMapping("/list")
    @ResponseBody()
    public PageUtils list(@RequestParam Map<String, Object> params){
        Query query = new Query(params);
        List<ProjectSummaryDO> project = projectSummaryService.list(query);
        int total = projectSummaryService.count(query);
        PageUtils pageUtil = new PageUtils(project, total);
        return pageUtil;
    }
}
