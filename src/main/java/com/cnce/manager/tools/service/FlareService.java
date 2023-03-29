package com.cnce.manager.tools.service;


import com.cnce.system.domain.UserDO;

/**
 * 工具管理
 *
 * @author Alan
 * @email chenfei917@163.com
 * @date 2017-09-27 14:28:36
 */
public interface FlareService {
    String  startTask(String projectName,String cameraLens,String testScene,String path,String name,String number,String equipName,UserDO user);

    boolean cancelTask(String equipName);

}
