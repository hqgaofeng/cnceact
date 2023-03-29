package com.cnce.manager.device.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.controller.BaseController;
import com.cnce.common.utils.PageUtils;
import com.cnce.common.utils.Query;
import com.cnce.common.utils.R;
import com.cnce.manager.device.domain.DeviceDO;
import com.cnce.manager.device.service.DeviceService;
import com.cnce.system.domain.UserDO;
import com.cnce.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RequestMapping("/manager/device")
@Controller
public class DeviceController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private String prefix="manager/device";

	@Autowired
	private DeviceService devService;


	@RequiresPermissions("manager:device:device")
	@GetMapping()
	public String device() {
		return prefix + "/device";
	}

	@GetMapping("/list")
	@ResponseBody
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<DeviceDO> device = devService.list(query);
		int total = devService.count(query);
		PageUtils pageUtil = new PageUtils(device, total);
		return pageUtil;
	}

	@RequiresPermissions("manager:device:add")
	@Log("添加需求")
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	@RequiresPermissions("manager:device:edit")
	@Log("编辑需求")
	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") int id) {
		try {
			DeviceDO device = devService.get(id);
			model.addAttribute("device", device);
			return prefix+"/edit";
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@RequiresPermissions("manager:device:add")
	@Log("保存设备信息")
	@PostMapping("/save")
	@ResponseBody
	public R save(DeviceDO device) {
		if (devService.save(device) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@RequiresPermissions("manager:device:edit")
	@Log("更新设备信息")
	@PostMapping("/update")
	@ResponseBody
	public R update(DeviceDO device) {
		if (devService.update(device) > 0) {
			return R.ok();
		}
		return R.error();
	}


	@RequiresPermissions("manager:device:remove")
	@Log("删除设备")
	@PostMapping("/remove")
	@ResponseBody
	public R remove(int id) {
		if (devService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@RequiresPermissions("manager:device:batchRemove")
	@Log("批量删除")
	@PostMapping("/batchRemove")
	@ResponseBody
	public R batchRemove(@RequestParam("ids[]") int[] devIds) {
		int r = devService.batchRemove(devIds);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}

}
