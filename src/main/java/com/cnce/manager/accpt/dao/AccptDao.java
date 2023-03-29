package com.cnce.manager.accpt.dao;

import com.cnce.manager.accpt.domain.AccptDO;
import com.cnce.manager.require.domain.RequireDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AccptDao {

    List<AccptDO> getAccptInfo(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(AccptDO accpt);
}
