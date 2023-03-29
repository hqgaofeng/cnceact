package com.cnce.project.project.service;

import com.cnce.project.project.domain.ProjectPowerDetailDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ProjectPowerDetailService {

    List<ProjectPowerDetailDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

}
