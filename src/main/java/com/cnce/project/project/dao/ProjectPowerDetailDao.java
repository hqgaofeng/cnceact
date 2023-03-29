package com.cnce.project.project.dao;

import com.cnce.project.project.domain.ProjectPowerDetailDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface ProjectPowerDetailDao {

    List<ProjectPowerDetailDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

}

