package com.cnce.manager.report.dao;

import com.cnce.manager.report.domain.ReportDO;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;
import java.util.Map;

@Mapper
public interface ReportDao {

    List<ReportDO> getReportInfo(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(ReportDO reportDO);
	
	 ReportDO getReportPath(Integer id);
}
