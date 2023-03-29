package com.cnce.manager.logs.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.utils.PageUtils;
import com.cnce.common.utils.Query;
import com.cnce.manager.logs.domain.LogsDO;
import com.cnce.manager.logs.service.LogsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/manager/logs")
public class logsController {

    private final String prefix = "manager/logs";
    @Autowired
    private LogsService logsServic;

    @GetMapping()
    @RequiresPermissions("manager:logs:logs")
    String goReport() {
        return prefix + "/logs";
    }

    @Log("开始下发命令")
    @GetMapping("/logsInfo")
    @ResponseBody()
    public PageUtils getReportInfo(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);

        List<LogsDO> appList = logsServic.getlogsInfo(query);

        int total =logsServic.count(query);
        PageUtils pageUtil = new PageUtils(appList, total);
        return pageUtil;
    }

    @Log("开始下发命令")
    @GetMapping("/download")
    @ResponseBody()
    public void downloadFile(Integer id, HttpServletResponse response) {
         logsServic.downFile(id,response);
    }

}
