package com.cnce.manager.tools.service;

import com.cnce.system.domain.UserDO;

import java.util.Map;

/**
 * 工具管理
 *
 * @author Alan
 * @email chenfei917@163.com
 * @date 2017-09-27 14:28:36
 */
public interface Camera3AService {

    String startTask(Map<String, String> params, UserDO user);

    boolean cancelTask(String equipName);
}
