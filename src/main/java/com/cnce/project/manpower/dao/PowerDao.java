package com.cnce.project.manpower.dao;

import com.cnce.project.manpower.domain.PowerDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface PowerDao {

    List<PowerDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int remove(int id);

}
