package com.cnce.manager.tools.service;


import com.cnce.system.domain.UserDO;

/**
 * 工具管理
 *
 * @author Alan
 * @email chenfei917@163.com
 * @date 2017-09-27 14:28:36
 */
public interface CameraService {
    String  startTask(String projectName, String cameraType, String path, String folderName,
                   String equipName, UserDO user);

    boolean cancelTask(String equipName, UserDO user);

}
