package com.cnce.project.manpower.service;

import com.cnce.project.manpower.domain.PowerDO;
import com.cnce.system.domain.UserDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface PowerService {

    List<PowerDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int remove(int id);

    List<Map<String, Object>> getList(Map<String, Object> map);

    int savePowers(Map<String, Object> map, UserDO userDO);

}
