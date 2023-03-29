package com.cnce.manager.logs.dao;

import com.cnce.manager.logs.domain.LogsDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LogsDao {

    List<LogsDO> getLogsInfo(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(LogsDO logDO);
	
	LogsDO getLogsPath(Integer id);
}
