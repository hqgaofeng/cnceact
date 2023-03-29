package com.cnce.manager.require.controller;
import com.cnce.common.annotation.Log;
import com.cnce.common.utils.PageUtils;
import com.cnce.common.utils.Query;
import com.cnce.common.utils.R;
import com.cnce.manager.file.SmbUtil;
import org.springframework.ui.Model;
import com.cnce.manager.require.domain.RequireDO;
import com.cnce.manager.require.service.RequireService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/manager/require")
public class RequireController {

    private final String prefix = "manager/require";

    @Autowired
    private RequireService requireServic;

    @GetMapping()
    @RequiresPermissions("manager:require:require")
    String goRequire() {
        return prefix + "/require";
    }


    @Log("开始下发命令")
    @GetMapping("/requireInfo")
    @ResponseBody()
    public PageUtils list(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<RequireDO> appList = requireServic.list(query);
        int total = requireServic.count(query);
        PageUtils pageUtil = new PageUtils(appList, total);
        return pageUtil;
    }

    @Log("添加需求")
    @GetMapping("/add")
    @RequiresPermissions("manager:require:add")
    public String add() {
        return prefix + "/add";
    }

    @Log("保存项目信息")
    @RequestMapping("/save")
    @ResponseBody()
    @RequiresPermissions("manager:require:add")
    public R save(RequireDO temp) {
        if (requireServic.save(temp) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("manager:require:edit")
    @Log("更新需求")
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        RequireDO require = requireServic.get(id);
        model.addAttribute("require", require);
        return prefix+"/edit";
    }

    @Log("更新需求")
    @RequestMapping("/update")
    @ResponseBody()
    @RequiresPermissions("manager:require:edit")
    public R update(RequireDO require) {
        if (requireServic.update(require) > 0) {
            return R.ok();
        }
        return R.error();
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
                String basePath = "/cnce/require/";
                smb.uploadFile(file,basePath);
            } catch (Exception e) {
                System.out.println("文件上传异常");
            }
        }
    }
}