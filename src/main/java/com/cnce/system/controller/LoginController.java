package com.cnce.system.controller;

import com.cnce.cache.TokenCacher;
import com.cnce.common.annotation.Log;
import com.cnce.common.controller.BaseController;
import com.cnce.common.domain.DictDO;
import com.cnce.common.domain.FileDO;
import com.cnce.common.domain.Tree;
import com.cnce.common.service.DictService;
import com.cnce.common.service.FileService;
import com.cnce.common.utils.ResultData;
import com.cnce.common.utils.ShiroUtils;
import com.cnce.system.domain.MenuDO;
import com.cnce.system.domain.UserDO;
import com.cnce.system.service.LoginService;
import com.cnce.system.service.MenuService;
import com.cnce.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class LoginController extends BaseController {

	@Autowired
	private MenuService menuService;
	@Autowired
	private FileService fileService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private UserService userService;
	@Autowired
	private DictService dictService;

	@GetMapping({ "/", "" })
	public String welcome(Model model) {
		return "redirect:/login";
	}

	@Log("请求访问主页")
	@GetMapping({ "/index" })
	public String index(Model model) {
		List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
		model.addAttribute("menus", menus);
		model.addAttribute("name", getUser().getName());
		FileDO fileDO = fileService.get(getUser().getPicId());
		if(fileDO!=null&&fileDO.getUrl()!=null){
			if(fileService.isExist(fileDO.getUrl())){
				model.addAttribute("picUrl",fileDO.getUrl());
			}else {
				model.addAttribute("picUrl","/img/logo.png");
			}
		}else {
			model.addAttribute("picUrl","/img/logo.png");
		}
		model.addAttribute("personal","/img/personal.png");
		model.addAttribute("username", getUser().getUsername());
		return "index_v1";
	}

	@GetMapping("/toPwdUpdate")
	public String toPwdUpdate(Model model) {
		//
		UserDO userDO  = userService.get(getUserId());
		model.addAttribute("user",userDO);
		List<DictDO> dds = dictService.listByType("pwd_time_limit");
		int pwdLimit = 3;//默认3个月
		if(!CollectionUtils.isEmpty(dds)){
			pwdLimit = Integer.parseInt(dds.get(0).getValue());
		}
		model.addAttribute("pwdLimit",pwdLimit);
		return "pwd_update";
	}

	@Log("登录")
	@PostMapping("/login")
	@ResponseBody
	public ResultData ajaxLogin(HttpServletRequest request,String username, String password) {
		return loginService.ajaxLogin(request,username,password);
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/logout")
	public String logout() {
		ShiroUtils.logout();
		return "redirect:/login";
	}

	@PostMapping("/appLogout")
	@ResponseBody
	public ResultData appLogout() {
		UserDO user = ShiroUtils.getUser();
		ShiroUtils.logout();
		String uname = null==user?"":user.getUsername();
		TokenCacher.getInstance().removeToken(uname);
		return new ResultData("200",true,"logout success");
	}

	@GetMapping("/main")
	public String main() {
		return "main";
	}

	@GetMapping("/403")
	public String error403() {
		return "403";
	}

}
