package com.cnce.system.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.controller.BaseController;
import com.cnce.common.utils.PageUtils;
import com.cnce.common.utils.Query;
import com.cnce.common.utils.R;
import com.cnce.manager.require.domain.RequireDO;
import com.cnce.system.domain.ManagerDO;
import com.cnce.system.domain.UserDO;
import com.cnce.system.service.impl.ManagerServiceImpl;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/system/manager")
@Controller
public class ManagerController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String prefix="system/manager";

    @Autowired
    private ManagerServiceImpl managerService;


    @RequiresPermissions("sys:manager:manager")
    @GetMapping("")
    public String user(Model model) {
        return prefix + "/manager";
    }

    @GetMapping("/list")
    @ResponseBody
    public PageUtils list(@RequestParam Map<String, Object> params) {
        // 查询列表数据
        Query query = new Query(params);
       List<ManagerDO> list = managerService.getToolsInfo(query);
        int total = managerService.count(query);
        PageUtils pageUtil = new PageUtils(list, total);
        return pageUtil;
    }


    @Log("添加需求")
    @GetMapping("/add")
    @RequiresPermissions("sys:manager:add")
    public String add() {
        return prefix + "/add";
    }

    @Log("保存项目信息")
    @RequestMapping("/save")
    @ResponseBody()
    @RequiresPermissions("sys:manager:add")
    public R save(ManagerDO temp) {
        if (managerService.save(temp) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("sys:manager:edit")
    @Log("更新需求")
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        ManagerDO manager = managerService.get(id);
        model.addAttribute("manager", manager);
        return prefix+"/edit";
    }

    @Log("更新需求")
    @RequestMapping("/update")
    @ResponseBody()
    @RequiresPermissions("sys:manager:edit")
    public R update(ManagerDO manager) {
        if (managerService.update(manager) > 0) {
            return R.ok();
        }
        return R.error();
    }


    @RequiresPermissions("sys:manager:remove")
    @Log("删除项目")
    @PostMapping("/remove")
    @ResponseBody
    public R remove(int id) {
        if (managerService.remove(id)  > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("sys:manager:batchRemove")
    @Log("批量删除项目")
    @PostMapping("/batchRemove")
    @ResponseBody
    public R batchRemove(@RequestParam("ids[]") int[] pids) {
        int r = managerService.batchRemove(pids);
        if (r > 0) {
            return R.ok();
        }
        return R.error();
    }

}
