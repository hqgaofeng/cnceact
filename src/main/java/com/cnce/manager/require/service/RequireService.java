package com.cnce.manager.require.service;

import com.cnce.manager.require.domain.RequireDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface RequireService {

    RequireDO get(int id);

    List<RequireDO> list(Map<String,Object> map);

    int count(Map<String, Object> map);

    int save (RequireDO require);

    int update(RequireDO require);

}
