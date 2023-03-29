package com.cnce.system.service;

import com.cnce.manager.require.domain.RequireDO;
import com.cnce.system.domain.ManagerDO;

import java.util.List;
import java.util.Map;

public interface ManagerService {

    List<ManagerDO> getToolsInfo(Map<String, Object> params);

    int count(Map<String, Object> params);

    ManagerDO get(Integer id);

    int save (ManagerDO manager);

    int  update(ManagerDO manager);

    int remove(Integer id);

    int  batchRemove(int[] pids);
}
