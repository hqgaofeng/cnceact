package com.cnce.manager.tools.service;

import com.cnce.system.domain.UserDO;

public interface UpgradeBlueService {


    String downLoad(String localPath, String remotePath, String packType,
                  String equipName, UserDO user);

    String onlyUpgrade(String localPath, String platformType, String packType,
                     String equipName, UserDO user);

    String upgradeAndRoot(String localPath, String platformType, String packType,
                        String equipName, UserDO user);

    String skipWizad(String equipName, UserDO user);

    String fullProcess(String localPath, String remotePath, String platformType, String packType,
                     String equipName, UserDO user);
}
