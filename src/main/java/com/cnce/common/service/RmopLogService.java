package com.cnce.common.service;

import com.cnce.common.domain.RmopLogDO;

import java.util.List;
import java.util.Map;

public interface RmopLogService {

    RmopLogDO get(Long id);

    List<RmopLogDO> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    int save(String deviceNo,String method,String operation);

    int batchRemove(Long[] ids);

}
