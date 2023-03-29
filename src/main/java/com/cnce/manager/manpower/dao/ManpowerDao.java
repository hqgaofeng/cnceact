package com.cnce.manager.manpower.dao;

import com.cnce.manager.manpower.domain.ManpowerDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ManpowerDao {

    List<ManpowerDO> loadTools(Map<String,Object> map);

    int count(Map<String, Object> map);

    int save (ManpowerDO manpowerDO);

    int insertList(@Param(value = "list") List<ManpowerDO> list);

}
