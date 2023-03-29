package com.cnce.project.project.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.controller.BaseController;
import com.cnce.common.utils.PageUtils;
import com.cnce.common.utils.Query;
import com.cnce.common.utils.R;
import com.cnce.project.project.domain.ProjectPlanDO;
import com.cnce.project.project.service.ProjectPlanService;
import com.cnce.project.project.service.ProjectService;
import com.cnce.system.domain.UserDO;
import com.cnce.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RequestMapping("/project/plan")
@Controller
public class ProjectPlanController extends BaseController {

    private final String prefix = "project/proplan";

    @Autowired
    private ProjectPlanService planService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;

    @RequiresPermissions("project:plan:add")
    @GetMapping("/add")
    public String add(Model model){
        return prefix + "/add";
    }

    @RequiresPermissions("project:plan:plan")
    @GetMapping()
    public String template(Model model) {
        return prefix + "/plan";
    }

    @GetMapping("/list")
    @ResponseBody()
    public PageUtils list(@RequestParam Map<String, Object> params){
        Query query = new Query(params);
        List<ProjectPlanDO> plan = planService.list(query);
        int total = planService.count(query);
        PageUtils pageUtil = new PageUtils(plan, total);
        return pageUtil;
    }

    @Log("保存项目计划")
    @RequestMapping("/save")
    @ResponseBody()
    @RequiresPermissions("project:plan:add")
    public R save(ProjectPlanDO plan) {
        UserDO user = userService.get(getUserId());
        String planName = plan.getPlanName();
        ProjectPlanDO projectPlan = planService.getProjectByPlan(planName);
        if(projectPlan != null){
            return R.error("计划名称已存在，请重新录入");
        }
        if (planService.save(plan, user) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("project:plan:edit")
    @Log("更新项目计划")
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        ProjectPlanDO plan = planService.get(id);
        model.addAttribute("temp", plan);
        return prefix+"/edit";
    }

    @Log("更新项目计划")
    @RequestMapping("/update")
    @ResponseBody()
    @RequiresPermissions("project:plan:edit")
    public R update(ProjectPlanDO plan) {
        UserDO user = userService.get(getUserId());
        String planName = plan.getPlanName();
        ProjectPlanDO projectPlan = planService.getProjectByPlan(planName);
        if(projectPlan != null){
            return R.error("计划名称已存在，请重新录入");
        }
        if (planService.update(plan, user) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("project:plan:remove")
    @Log("删除计划")
    @PostMapping("/remove")
    @ResponseBody
    public R remove(int id) {
        if(planService.remove(id) > 0){
            return R.ok();
        }
        return R.error();

    }

    @RequiresPermissions("project:plan:batchRemove")
    @Log("批量删除")
    @PostMapping("/batchRemove")
    @ResponseBody
    public R batchRemove(@RequestParam("ids[]") int[] bids) {
        int r = planService.batchRemove(bids);
        if (r > 0) {
            return R.ok();
        }
        return R.error();
    }

    @GetMapping("/loadProject")
    @ResponseBody()
    public List<String> loadProject(){
        List<String> proList = projectService.loadProject();
        return proList;
    }

}
