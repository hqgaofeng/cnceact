package com.cnce.manager.tools.service;


import com.cnce.system.domain.UserDO;

import java.util.Map;

public interface MachineService {

    boolean beginTask(Map<String, String> params);

    int saveLogs(UserDO user);

    int saveResult(UserDO user);

}
