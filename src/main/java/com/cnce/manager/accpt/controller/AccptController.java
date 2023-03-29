package com.cnce.manager.accpt.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.controller.BaseController;
import com.cnce.common.utils.PageUtils;
import com.cnce.common.utils.Query;
import com.cnce.common.utils.R;
import com.cnce.manager.accpt.domain.AccptDO;
import com.cnce.manager.accpt.service.AccptService;
import com.cnce.manager.file.SmbUtil;
import com.cnce.system.domain.UserDO;
import com.cnce.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/manager/accpt")
public class AccptController extends BaseController {

    private final String prefix = "manager/accpt";
    @Autowired
    private AccptService accptServic;
    @Autowired
    private UserService userService;

    @GetMapping()
    @RequiresPermissions("manager:accpt:accpt")
    String goReport() {
        return prefix + "/accpt";
    }

    @Log("添加需求")
    @GetMapping("/add")
    @RequiresPermissions("manager:accpt:add")
    public String add() {
        return prefix + "/add";
    }

    @Log("开始下发命令")
    @GetMapping("/accptInfo")
    @ResponseBody()
    public PageUtils getAccptInfo(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);

        List<AccptDO> appList = accptServic.getAccptInfo(query);

        int total = accptServic.count(query);
        PageUtils pageUtil = new PageUtils(appList, total);
        return pageUtil;
    }
    @RequestMapping("/fileUpload")
    @ResponseBody
    public void fileUpload(MultipartFile file) {
        Calendar now = Calendar.getInstance();
        if (file.isEmpty()) {
            return;
        } else {
            try {
                SmbUtil smb = new SmbUtil();
                String basePath = "/cnce/acceptance/";
                smb.uploadFile(file,basePath);
            } catch (Exception e) {
                System.out.println("文件上传异常");
            }
        }
    }
//    @Log("添加需求")
//    @RequestMapping("/save")
//    @RequiresPermissions("manager:accpt:add")
//    @ResponseBody
//    public int save(
//            @RequestParam("toolName") String toolName,
//            @RequestParam("opinion") String opinion, @RequestParam("accptResult") String accptResult,
//            @RequestParam("accptTime") String accptTime) {
//        UserDO user = userService.get(getUserId());
//        Long userId=user.getUserId();
//        String userName=user.getName();
//        int count = accptServic.save(toolName, opinion, accptResult, accptTime,userId,userName);
//
//        return count;
//
//    }


    @Log("保存项目信息")
    @RequestMapping("/save")
    @ResponseBody()
    @RequiresPermissions("manager:accpt:add")
    public R save(AccptDO temp) {
        UserDO user = userService.get(getUserId());
        Long userId=user.getUserId();
        String userName=user.getName();
        temp.setUserName(userName);
        temp.setUserId(Math.toIntExact(userId));
        if (accptServic.save(temp) > 0) {
            return R.ok();
        }
        return R.error();
    }
}
