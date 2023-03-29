package com.cnce.manager.tools.service;

import org.springframework.stereotype.Service;

import java.util.List;

public interface EquipService {

    List<String> getEquipName(Integer deptId);

    List<String> getEquipIp(Integer deptId);
}
