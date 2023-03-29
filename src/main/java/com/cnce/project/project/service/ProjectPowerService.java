package com.cnce.project.project.service;

import com.cnce.project.project.domain.ProjectPowerDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ProjectPowerService {

    List<ProjectPowerDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    String loadTotalPower();

}
