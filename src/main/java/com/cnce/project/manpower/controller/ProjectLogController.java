package com.cnce.project.manpower.controller;


import com.cnce.common.annotation.Log;
import com.cnce.common.controller.BaseController;
import com.cnce.common.utils.PageUtils;
import com.cnce.common.utils.Query;
import com.cnce.common.utils.StringUtils;
import com.cnce.project.manpower.domain.ProjectLogDO;
import com.cnce.project.manpower.service.ProjectLogService;
import net.sf.ehcache.search.query.QueryManager;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/project/log")
@Controller
public class ProjectLogController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String prefix = "project/log";

    @Autowired
    private ProjectLogService logService;

    @RequiresPermissions("project:log:log")
    @GetMapping()
    public String manPower(Model model) {
        return prefix + "/log";
    }

    @Log("操作日志查询")
    @RequestMapping("/logsInfo")
    @ResponseBody()
    public PageUtils getAllLogInfo(@RequestBody Map<String, Object> params) {
        Object endTimeObj = params.get("endTime");
        String endTime = endTimeObj != null ? endTimeObj.toString() : null;

        if (StringUtils.isEmpty(endTime)) {
            params.remove("endTime"); // 如果 endTime 参数为空，则将其从查询参数中移除
        } else {
            SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
            Calendar calendar = Calendar.getInstance();
            Date date = null;
            try {
                date = df.parse(endTime);
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, 1); // 增加一天
                String newEndTime = df.format(calendar.getTime());
                params.put("endTime", newEndTime);
            } catch (ParseException e) {
                // 如果 endTime 参数不符合日期格式，则不进行转换
                e.printStackTrace();
            }
        }
        Query query = new Query(params);
        List<ProjectLogDO> appList = logService.getAllLogInfo(query);
        int total = logService.count(query);
        PageUtils pageUtil = new PageUtils(appList, total);
        return pageUtil;
    }
    private static final String DATE_FORMAT = "yyyy-MM-dd";
 }





