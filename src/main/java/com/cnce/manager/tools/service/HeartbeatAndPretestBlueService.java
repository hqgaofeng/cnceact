package com.cnce.manager.tools.service;

import com.cnce.manager.tools.domain.HeartbeatAndPretestBlueDO;
import com.cnce.system.domain.UserDO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 工具管理
 *
 * @author Alan
 * @email chenfei917@163.com
 * @date 2017-09-27 14:28:36
 */
public interface HeartbeatAndPretestBlueService {

    List<HeartbeatAndPretestBlueDO> getAllAppInfo(Map<String, Object> map);

    int count(Map<String, Object> map);

    String startTest(String[] names, int num, String equipName, UserDO user);

    boolean cancelTest(boolean flag,String equipName);

}
