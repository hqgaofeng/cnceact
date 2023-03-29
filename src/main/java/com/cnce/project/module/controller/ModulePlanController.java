package com.cnce.project.module.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.controller.BaseController;
import com.cnce.common.utils.R;
import com.cnce.project.business.service.BaselineService;
import com.cnce.project.module.service.ModulePlanService;
import com.cnce.project.project.service.ProjectPlanService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RequestMapping("/module/plan")
@Controller
public class ModulePlanController extends BaseController {

    private final String prefix = "project/modplan";

    @Autowired
    private ModulePlanService planService;
    @Autowired
    private ProjectPlanService proPlanService;
    @Autowired
    private BaselineService baseService;

    @RequiresPermissions("module:plan:plan")
    @GetMapping()
    public String template(Model model) {
        return prefix + "/plan";
    }

    @GetMapping("/list")
    @ResponseBody
    public List<Map<String, Object>> list(@RequestParam Map<String, Object> params) {
        // 查询列表数据
        List<Map<String, Object>> data = planService.loadData(params);
        return data;
    }

    @Log("模块人力数据保存")
    @RequestMapping(value = "/saveData", method = RequestMethod.POST)
    @ResponseBody
    public R saveData(@RequestBody Map<String, Object> map) {
        if (planService.saveDatas(map) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @GetMapping("/getModule")
    @ResponseBody()
    public List<String> loadModule(){
        List<String> modules = baseService.loadModules();
        return modules;
    }

    @GetMapping("/getProjectPlan")
    @ResponseBody()
    public List<String> loadProject(){
        List<String> plan = proPlanService.loadProjectPlan();
        return plan;
    }

}
