package com.cnce.manager.profit.service;

import com.cnce.manager.profit.domain.ProfitDO;

import java.util.List;
import java.util.Map;

public interface ProfitService {

    List<ProfitDO> list(Map<String, Object> map);

    int save(ProfitDO profit);

}
