package com.cnce.manager.tools.service;


import com.cnce.system.domain.UserDO;

import java.util.Map;

public interface PDUService {

    boolean beginTask(Map<String, String> params);

    boolean cancelTask(String devName);

    int saveLogs(UserDO user);

    int saveResult(UserDO user);

}
