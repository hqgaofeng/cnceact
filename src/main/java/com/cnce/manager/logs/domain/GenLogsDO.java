package com.cnce.manager.logs.domain;

import com.cnce.common.utils.SFTPUtils;
import com.cnce.system.domain.UserDO;

import java.util.Date;

public class GenLogsDO {

    public static LogsDO loadDO(UserDO user, Integer tooId, Integer status, String toolName, String logPath){
        String reportPath = null;
        if(SFTPUtils.login()){
            String path = SFTPUtils.getFileProperties(SFTPUtils.conn, logPath);
            String newPath = SFTPUtils.getFileProperties(SFTPUtils.conn, path);
            if(newPath != null){
                reportPath = newPath.substring(0, newPath.length()-1);
            }
        }
        LogsDO logDO = new LogsDO();
        logDO.setToolId(tooId);
        logDO.setToolName(toolName);
        logDO.setUserId(user.getUserId().intValue());
        logDO.setUserName(user.getName());
        logDO.setStatus(status);
        logDO.setLogPath(reportPath);
        logDO.setCreateTime(new Date());
        return logDO;
    }
}
