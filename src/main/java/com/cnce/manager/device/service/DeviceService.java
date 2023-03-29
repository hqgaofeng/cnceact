package com.cnce.manager.device.service;

import com.cnce.manager.device.domain.DeviceDO;
import com.cnce.system.domain.UserDO;

import java.util.List;
import java.util.Map;

/**
 * 设备管理
 *
 * @author Alan
 * @email chenfei917@163.com
 * @date 2017-09-27 14:28:36
 */
public interface DeviceService {

    DeviceDO getDev(UserDO userDO);

    DeviceDO get(int deviceId);

    List<DeviceDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(DeviceDO device);

    int update(DeviceDO device);

    int remove(int deviceId);

    int batchRemove(int[] devIds);
}
