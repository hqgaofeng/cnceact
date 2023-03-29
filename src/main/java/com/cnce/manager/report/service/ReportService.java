package com.cnce.manager.report.service;

import com.cnce.manager.report.domain.ReportDO;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface ReportService {

    List<ReportDO> getReportInfo(Map<String,Object> map);

    int count(Map<String, Object> map);

    String getReportPath(Integer id);

    void downFile(Integer id, HttpServletResponse response);

}
