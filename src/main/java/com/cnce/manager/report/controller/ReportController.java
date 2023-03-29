package com.cnce.manager.report.controller;

import com.cnce.common.annotation.Log;
import com.cnce.common.utils.PageUtils;
import com.cnce.common.utils.Query;
import com.cnce.manager.report.dao.ReportDao;
import com.cnce.manager.report.domain.ReportDO;
import com.cnce.manager.report.service.ReportService;
import com.cnce.manager.tools.domain.TopAPPDO;
import com.cnce.manager.tools.service.Camera3AService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/manager/report")
public class ReportController {

    private final String prefix = "manager/report";
    @Autowired
    private ReportService reportServic;

    @GetMapping()
    @RequiresPermissions("manager:report:report")
    String goReport() {
        return prefix + "/report";
    }

    @Log("开始下发命令")
    @GetMapping("/reportInfo")
    @ResponseBody()
    public PageUtils getReportInfo(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);

        List<ReportDO> appList = reportServic.getReportInfo(query);

        int total = reportServic.count(query);
        PageUtils pageUtil = new PageUtils(appList, total);
        return pageUtil;
    }

    @Log("开始下发命令")
    @GetMapping("/download")
    @ResponseBody()
    public void downloadFile( Integer id, HttpServletResponse response) {
        reportServic.downFile(id,response);
    }


}
