package com.cnce.manager.tools.service;

import com.cnce.manager.tools.domain.WifiDO;
import com.cnce.system.domain.UserDO;

public interface WifiService {

    boolean beginTask(WifiDO wifiDO);

    int saveLogs(UserDO user);

    int saveResult(UserDO user);

}
