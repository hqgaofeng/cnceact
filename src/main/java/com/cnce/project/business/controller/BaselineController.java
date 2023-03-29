package com.cnce.project.business.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.service.FileService;
import com.cnce.common.utils.PageUtils;
import com.cnce.common.utils.Query;
import com.cnce.common.utils.R;
import com.cnce.project.business.domain.BaselineDO;
import com.cnce.project.business.service.BaselineService;
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


@RequestMapping("/project/baseline")
@Controller
public class BaselineController {

    private final String prefix = "project/baseline";

    @Autowired
    private BaselineService baseService;
    @Autowired
    private FileService fileService;

    @RequiresPermissions("project:baseline:add")
    @GetMapping("/add")
    public String add(Model model){
        return prefix + "/add";
    }

    @RequiresPermissions("project:baseline:baseline")
    @GetMapping()
    public String template(Model model) {
        return prefix + "/baseline";
    }

    @GetMapping("/list")
    @ResponseBody()
    public PageUtils list(@RequestParam Map<String, Object> params){
        Query query = new Query(params);
        List<BaselineDO> baseline = baseService.list(query);
        int total = baseService.count(query);
        PageUtils pageUtil = new PageUtils(baseline, total);
        return pageUtil;
    }

    @Log("保存项目信息")
    @RequestMapping("/save")
    @ResponseBody()
    @RequiresPermissions("project:baseline:add")
    public R save(BaselineDO baseline) {
        if (baseService.save(baseline) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("project:baseline:edit")
    @Log("更新项目")
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        BaselineDO baseline = baseService.get(id);
        model.addAttribute("temp", baseline);
        return prefix+"/edit";
    }

    @Log("更新项目信息")
    @RequestMapping("/update")
    @ResponseBody()
    @RequiresPermissions("project:baseline:edit")
    public R update(BaselineDO baseline) {
        if (baseService.update(baseline) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("project:baseline:remove")
    @Log("删除基线")
    @PostMapping("/remove")
    @ResponseBody
    public R remove(int id) {
        if(baseService.remove(id) > 0){
            return R.ok();
        }
        return R.error();

    }

    @RequiresPermissions("project:baseline:batchRemove")
    @Log("批量删除")
    @PostMapping("/batchRemove")
    @ResponseBody
    public R batchRemove(@RequestParam("ids[]") int[] pids) {
        int r = baseService.batchRemove(pids);
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
            List<String> obList = new ArrayList<>();
            obList.add("scope");
            obList.add("module");
            obList.add("baseline");
            List<Map> userList = fileService.getDataByExcel(obList, in,fileName);
            // 把数据插入数据库
            boolean result = baseService.batchImportBaseline(userList);
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
