package com.cnce.manager.tools.service;

import com.cnce.system.domain.UserDO;

public interface UpgradeService {


    String onlyUpgrade(String localPath, String platformType, String packType, String equipName, UserDO user);

    String upgradeAndRoot(String localPath, String platformType, String packType, String equipName, UserDO user);

    String skipWizad(String equipName, UserDO user);
}
