package com.cnce.project.staff.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.service.FileService;
import com.cnce.common.utils.PageUtils;
import com.cnce.common.utils.Query;
import com.cnce.common.utils.R;
import com.cnce.project.staff.domain.StaffDO;
import com.cnce.project.staff.service.StaffService;
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


@RequestMapping("/project/staff")
@Controller
public class StaffController {

    private final String prefix = "project/staff";

    @Autowired
    private StaffService staffService;
    @Autowired
    private FileService fileService;

    @RequiresPermissions("project:staff:add")
    @GetMapping("/add")
    public String add(Model model){
        return prefix + "/add";
    }

    @RequiresPermissions("project:staff:staff")
    @GetMapping()
    public String template(Model model) {
        return prefix + "/staff";
    }

    @GetMapping("/list")
    @ResponseBody()
    public PageUtils list(@RequestParam Map<String, Object> params){
        Query query = new Query(params);
        List<StaffDO> staff = staffService.list(query);
        int total = staffService.count(query);
        PageUtils pageUtil = new PageUtils(staff, total);
        return pageUtil;
    }

    @Log("保存人员信息")
    @RequestMapping("/save")
    @ResponseBody()
    @RequiresPermissions("project:staff:add")
    public R save(StaffDO staff) {
        if (staffService.save(staff) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("project:staff:edit")
    @Log("更新人员信息")
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        StaffDO staff = staffService.get(id);
        model.addAttribute("temp", staff);
        return prefix+"/edit";
    }

    @RequestMapping("/update")
    @ResponseBody()
    @RequiresPermissions("project:staff:edit")
    public R update(StaffDO staff) {
        if (staffService.update(staff) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("project:staff:remove")
    @Log("删除人员")
    @PostMapping("/remove")
    @ResponseBody
    public R remove(int id) {
        if(staffService.remove(id) > 0){
            return R.ok();
        }
        return R.error();

    }

    @RequiresPermissions("project:staff:batchRemove")
    @Log("批量删除")
    @PostMapping("/batchRemove")
    @ResponseBody
    public R batchRemove(@RequestParam("ids[]") int[] bids) {
        int r = staffService.batchRemove(bids);
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
            obList.add("staffName");
            obList.add("sGroup");
            obList.add("property");
            obList.add("jobContent");
            obList.add("experience");
            List<Map> userList = fileService.getDataByExcel(obList, in,fileName);
            // 把数据插入数据库
            boolean result = staffService.batchImportStaff(userList);
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
