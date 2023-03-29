package com.cnce.manager.require.dao;

import com.cnce.manager.require.domain.RequireDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RequireDao {

    RequireDO get(int id);

    List<RequireDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(RequireDO require);

    int update(RequireDO require);
}
