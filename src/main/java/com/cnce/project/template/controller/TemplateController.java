package com.cnce.project.template.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.utils.PageUtils;
import com.cnce.common.utils.Query;
import com.cnce.common.utils.R;
import com.cnce.project.template.domain.TemplateDO;
import com.cnce.project.template.service.TempSyncService;
import com.cnce.project.template.service.TemplateService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("/project/template")
@Controller
public class TemplateController {

    private final String prefix = "project/template";

    @Autowired
    private TemplateService templateService;
    @Autowired
    private TempSyncService tmpSyncService;

    @RequiresPermissions("project:template:add")
    @GetMapping("/add")
    public String add(Model model){
        return prefix + "/add";
    }

    @RequiresPermissions("project:template:template")
    @GetMapping()
    public String template(Model model) {
        return prefix + "/template";
    }

    @GetMapping("/list")
    @ResponseBody()
    public PageUtils list(@RequestParam Map<String, Object> params){
        Query query = new Query(params);
        List<TemplateDO> tempList = templateService.list(query);
        int total = templateService.count(query);
        PageUtils pageUtil = new PageUtils(tempList, total);
        return pageUtil;
    }

    @Log("保存项目信息")
    @RequestMapping("/save")
    @ResponseBody()
    @RequiresPermissions("project:template:add")
    public R save(TemplateDO temp) {

        switch (templateService.save(temp)){
            case 1:return R.ok();
            case 2:return R.error("该项目此月份已存在");
            default:return R.error();
        }

    }

    @RequiresPermissions("project:template:edit")
    @Log("更新项目")
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        TemplateDO temp = templateService.get(id);
        model.addAttribute("temp", temp);
        return prefix+"/edit";
    }

    @Log("更新项目信息")
    @RequestMapping("/update")
    @ResponseBody()
    @RequiresPermissions("project:template:edit")
    public R update(TemplateDO temp) {
        if (templateService.update(temp) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("project:template:remove")
    @Log("删除项目")
    @PostMapping("/remove")
    @ResponseBody
    public R remove(int id) {
        System.out.println("id====="+id);
        if(templateService.isRev(id)){
            if(templateService.remove(id) > 0){
                return R.ok();
            }
            return R.error();
        }else {
            String msg = templateService.getMsg(id);
            return R.error(1, msg);
        }
    }

    @RequiresPermissions("project:template:batchRemove")
    @Log("批量删除项目")
    @PostMapping("/batchRemove")
    @ResponseBody
    public R batchRemove(@RequestParam("ids[]") int[] pids) {
        int r = templateService.batchRemove(pids);
        if (r > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("project:template:removal")
    @Log("人力撤出")
    @PostMapping("/removal")
    @ResponseBody
    public R removal(int id) {
        int r = templateService.removal(id);
        if(r > 0){
            return R.ok();
        }
        return R.error("该项目在当月没有人力数据");
    }

    @GetMapping("/loadMonth")
    @ResponseBody()
    public List<String> loadMonth(){
        List<String> monthList = templateService.loadMonth();
        return monthList;
    }

    @GetMapping("/loadProject")
    @ResponseBody()
    public List<String> loadProjects(String region){
        List<String> proList = templateService.loadProject(region);
        return proList;
    }

    @GetMapping("/getSyncProject")
    @ResponseBody()
    public List<String> getProjects(String region){
        List<String> projects = tmpSyncService.getProjects(region);
        return projects;
    }

}
