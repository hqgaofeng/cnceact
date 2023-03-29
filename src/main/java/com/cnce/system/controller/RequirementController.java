package com.cnce.system.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.controller.BaseController;
import com.cnce.common.utils.*;
import com.cnce.system.domain.RequirementDO;
import com.cnce.system.domain.UserDO;
import com.cnce.system.service.RequirementService;
import com.cnce.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/sys/require")
@Controller
public class RequirementController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private String prefix="system/require";

	@Autowired
	private RequirementService reqService;
	@Autowired
	private UserService userService;


	@RequiresPermissions("sys:require:require")
	@GetMapping()
	public String require() {
		return prefix + "/require";
	}

	@GetMapping("/list")
	@ResponseBody
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<RequirementDO> requires = reqService.list(query);
		int total = reqService.count(query);
		PageUtils pageUtil = new PageUtils(requires, total);
		return pageUtil;
	}

	@RequiresPermissions("sys:require:add")
	@Log("添加需求")
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	@RequiresPermissions("sys:require:edit")
	@Log("编辑需求")
	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") int id) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			RequirementDO require = reqService.get(id);
			require.setExpectTime(sf.parse(sf.format(require.getExpectTime())));
			model.addAttribute("require", require);
			return prefix+"/edit";
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@RequiresPermissions("sys:require:add")
	@Log("保存需求")
	@PostMapping("/save")
	@ResponseBody
	public R save(RequirementDO require) {
		UserDO userDO = userService.get(getUserId());
		require.setSponsor(userDO.getName());
		if (reqService.save(require) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@RequiresPermissions("sys:require:edit")
	@Log("更新需求")
	@PostMapping("/update")
	@ResponseBody
	public R update(RequirementDO require) {
		UserDO userDO = userService.get(getUserId());
		require.setSponsor(userDO.getName());
		if (reqService.update(require) > 0) {
			return R.ok();
		}
		return R.error();
	}


	@RequiresPermissions("sys:require:remove")
	@Log("删除需求")
	@PostMapping("/remove")
	@ResponseBody
	public R remove(int id) {
		if (reqService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@RequiresPermissions("sys:require:batchRemove")
	@Log("批量删除")
	@PostMapping("/batchRemove")
	@ResponseBody
	public R batchRemove(@RequestParam("ids[]") int[] reqIds) {
		int r = reqService.batchRemove(reqIds);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}

}
