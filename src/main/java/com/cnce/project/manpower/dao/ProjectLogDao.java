package com.cnce.project.manpower.dao;

import com.cnce.project.manpower.domain.ProjectLogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface ProjectLogDao {

    List<ProjectLogDO> getAllLogInfo(Map<String, Object> params);

    int saveLog(ProjectLogDO log);

    Integer count(Map<String, Object> params);
}
