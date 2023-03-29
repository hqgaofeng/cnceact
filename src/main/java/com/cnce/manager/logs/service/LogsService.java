package com.cnce.manager.logs.service;

import com.cnce.manager.logs.domain.LogsDO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface LogsService {

    List<LogsDO> getlogsInfo(Map<String,Object> map);

    int count(Map<String, Object> map);

    String getLogsPath(Integer id);

    void  downFile(Integer id, HttpServletResponse response);
}
