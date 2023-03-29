package com.cnce.project.project.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.service.FileService;
import com.cnce.common.utils.PageUtils;
import com.cnce.common.utils.Query;
import com.cnce.common.utils.R;
import com.cnce.project.project.domain.ProjectDO;
import com.cnce.project.project.service.ProjectService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RequestMapping("/project/project")
@Controller
public class ProjectController {

    private final String prefix = "project/project";

    @Autowired
    private ProjectService projectService;
    @Autowired
    private FileService fileService;

    @RequiresPermissions("project:project:add")
    @GetMapping("/add")
    public String add(Model model){
        return prefix + "/add";
    }

    @RequiresPermissions("project:project:project")
    @GetMapping()
    public String template(Model model) {
        return prefix + "/project";
    }

    @GetMapping("/list")
    @ResponseBody()
    public PageUtils list(@RequestParam Map<String, Object> params){
        Query query = new Query(params);
        List<ProjectDO> project = projectService.list(query);
        int total = projectService.count(query);
        PageUtils pageUtil = new PageUtils(project, total);
        return pageUtil;
    }

    @Log("保存项目信息")
    @RequestMapping("/save")
    @ResponseBody()
    @RequiresPermissions("project:project:add")
    public R save(ProjectDO project) {
        if (projectService.save(project) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("project:project:edit")
    @Log("更新项目")
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        ProjectDO project = projectService.get(id);
        model.addAttribute("temp", project);
        return prefix+"/edit";
    }

    @Log("更新项目信息")
    @RequestMapping("/update")
    @ResponseBody()
    @RequiresPermissions("project:project:edit")
    public R update(ProjectDO project) {
        if (projectService.update(project) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("project:project:remove")
    @Log("删除基线")
    @PostMapping("/remove")
    @ResponseBody
    public R remove(int id) {
        if(projectService.remove(id) > 0){
            return R.ok();
        }
        return R.error();

    }

    @RequiresPermissions("project:project:batchRemove")
    @Log("批量删除")
    @PostMapping("/batchRemove")
    @ResponseBody
    public R batchRemove(@RequestParam("ids[]") int[] bids) {
        int r = projectService.batchRemove(bids);
        if (r > 0) {
            return R.ok();
        }
        return R.error();
    }

    //@RequiresPermissions("project:project:fileImport")
    @Log("批量导入")
    @PostMapping("/fileImport")
    @ResponseBody
    public R fileImport(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        InputStream in = null;
        try{
            in = file.getInputStream();
            List<String> objList = new ArrayList<>();
            objList.add("proName");
            List<Map> projectList = fileService.getDataByExcel(objList, in,fileName);
            // 把数据插入数据库
            boolean result = projectService.batchImportProjects(projectList);
            if (result){
                return R.ok();
            }else {
                return R.error();
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            in.close();
        }
        return R.error();
    }

}
