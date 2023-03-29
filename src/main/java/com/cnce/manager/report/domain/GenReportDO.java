package com.cnce.manager.report.domain;

import com.cnce.common.utils.SFTPUtils;
import com.cnce.system.domain.UserDO;

import java.util.Date;

public class GenReportDO {

    public static ReportDO loadDO(UserDO user, Integer toolId, String toolName, String logPath){
        String reportPath = null;
        if(SFTPUtils.login()){
            String path = SFTPUtils.getFileProperties(SFTPUtils.conn, logPath);
            String newPath = SFTPUtils.getFileProperties(SFTPUtils.conn, path);
            if(newPath != null){
                reportPath = newPath.substring(0, newPath.length()-1);
            }
        }
        ReportDO reportDO = new ReportDO();
        reportDO.setToolId(toolId);
        reportDO.setToolName(toolName);
        reportDO.setUserId(user.getUserId().intValue());
        reportDO.setUserName(user.getName());
        reportDO.setReportPath(reportPath);
        reportDO.setCreateTime(new Date());
        return reportDO;
    }

}
