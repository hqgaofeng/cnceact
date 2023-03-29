package com.cnce.common.dao;

import com.cnce.common.domain.RmopLogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 *
 */
@Mapper
public interface RmopLogDao {

    RmopLogDO get(Long id);

    List<RmopLogDO> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    int save(RmopLogDO log);

    int batchRemove(Long[] ids);

}
