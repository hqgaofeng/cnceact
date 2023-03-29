package com.cnce.manager.tools.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.controller.BaseController;
import com.cnce.common.utils.PageUtils;
import com.cnce.common.utils.Query;
import com.cnce.manager.file.SmbUtil;
import com.cnce.manager.tools.domain.TopAPPDO;
import com.cnce.manager.tools.service.TopAPPService;
import com.cnce.system.domain.UserDO;
import com.cnce.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import java.util.List;
import java.util.Map;

/**
 * 工具管理
 *
 * @author Alan
 * @email chenfei917@163.com
 * @date 2017-09-27 14:40:36
 */

@Controller
@RequestMapping("/manager/topAppTools")
public class TopAPPController extends BaseController {

    private final String prefix = "manager/topAppTools";

    @Autowired
    private TopAPPService topAPPService;
    @Autowired
    private UserService userService;

    @GetMapping()
    @RequiresPermissions("manager:topAppTools:topApp")
    String tools() {
        return prefix + "/topApp";
    }

    @Log("开始下发命令")
    @GetMapping("/appInfo")
    @ResponseBody()
    public PageUtils getAllAppInfo(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<TopAPPDO> appList = topAPPService.getAllAppInfo(query);

        int total = topAPPService.count(query);
        PageUtils pageUtil = new PageUtils(appList, total);
        return pageUtil;
    }

    @Log("开始下发命令")
    @PostMapping("/batchInstall")
    @ResponseBody()
    public String batchInstall(@RequestParam("names[]") String[] names,@RequestParam("equipName")String equipName) {
        UserDO user = userService.get(getUserId());
        String result=topAPPService.batchInstall(names, equipName, user);
        return result;

    }

    @Log("开始下发命令")
    @PostMapping("/batchUninstall")
    @ResponseBody()
    public String batchUninstall(@RequestParam("appPackages[]") String[] appPackages,
                                 @RequestParam("equipName")String equipName) {
        UserDO user = userService.get(getUserId());
        topAPPService.batchUninstall(appPackages, equipName, user);
        return prefix + "/topApp";

    }

    @Log("获取AppType")
    @GetMapping("/appType")
    @ResponseBody()
    public List<String> getAppType() {
        List<String> appTypeList = topAPPService.getAppType();

        return appTypeList;
    }

    @Log("获取App细分领域")
    @GetMapping("/appField")
    @ResponseBody()
    public List<String> getAppField(@RequestParam("appType") String appType) {
        List<String> appFiledList = topAPPService.getAppField(appType);

        return appFiledList;
    }

    @PostMapping("/import")
    @ResponseBody()
    public void importFile(@RequestParam("file")  MultipartFile file) {
        try {
            topAPPService.importFiles(file);
            SmbUtil smb = new SmbUtil();
            String basePath = "/cnce/require/";
            smb.uploadFile(file,basePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
