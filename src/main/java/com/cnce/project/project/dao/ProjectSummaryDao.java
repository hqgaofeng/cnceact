package com.cnce.project.project.dao;

import com.cnce.project.project.domain.ProjectSummaryDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProjectSummaryDao {

    List<ProjectSummaryDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

}

