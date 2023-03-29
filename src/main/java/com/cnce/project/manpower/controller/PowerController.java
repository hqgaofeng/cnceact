package com.cnce.project.manpower.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.controller.BaseController;
import com.cnce.common.utils.R;
import com.cnce.project.manpower.service.PowerService;
import com.cnce.project.manpower.service.ProjectLogService;
import com.cnce.system.domain.UserDO;
import com.cnce.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/manpower/manpower")
@Controller
public class PowerController extends BaseController {

	private String prefix = "manpower/manpower";

	@Autowired
	private PowerService powerService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProjectLogService logService;

	@RequiresPermissions("manpower:manpower:manpower")
	@GetMapping()
	public String manPower(Model model) {
		return prefix + "/manpower";
	}

	@GetMapping("/list")
	@ResponseBody
	public List<Map<String, Object>> list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		List<Map<String, Object>> powers = powerService.getList(params);
		return powers;
	}

	@Log("人力预估数据保存")
	@RequestMapping(value = "/saveData", method = RequestMethod.POST)
	@ResponseBody
	public R saveData(@RequestBody Map<String, Object> map) {
		UserDO userDO = userService.get(getUserId());
		if (powerService.savePowers(map, userDO) > 0) {
			logService.savePowerLog(map, userDO);
			return R.ok();
		}
		return R.error();
	}

}
