package com.cnce.project.manpower.service;

import com.cnce.project.manpower.domain.ProjectLogDO;
import com.cnce.system.domain.UserDO;

import java.util.*;

public interface ProjectLogService {

    List<ProjectLogDO> getAllLogInfo(Map<String, Object> params);

    int savePowerLog(Map<String, Object> map, UserDO userDO);

    Integer count(Map<String, Object> params);
}
