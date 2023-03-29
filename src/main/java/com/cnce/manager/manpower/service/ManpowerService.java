package com.cnce.manager.manpower.service;

import com.cnce.manager.manpower.domain.ManPowerVO;
import com.cnce.manager.manpower.domain.ManpowerDO;

import java.util.List;
import java.util.Map;

public interface ManpowerService {

    ManPowerVO getAll();

    List<ManpowerDO> loadTools(Map<String,Object> map);

    int count(Map<String, Object> map);


}
