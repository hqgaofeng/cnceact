package com.cnce.manager.accpt.service;

import com.cnce.manager.accpt.domain.AccptDO;

import java.util.List;
import java.util.Map;

public interface AccptService {

    List<AccptDO>  getAccptInfo(Map<String,Object> map);

    int count(Map<String, Object> map);

//    int save(String toolName,String opinion,String accptResult,String accptTime,Long userId,String userName);
    int save(AccptDO accpt);
}
