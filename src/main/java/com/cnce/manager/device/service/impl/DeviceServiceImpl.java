package com.cnce.manager.device.service.impl;

import com.cnce.manager.device.dao.DeviceDao;
import com.cnce.manager.device.domain.DeviceDO;
import com.cnce.manager.device.service.DeviceService;
import com.cnce.system.dao.UserDao;
import com.cnce.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class DeviceServiceImpl implements DeviceService {


    @Autowired
    private DeviceDao deviceMapper;
    @Autowired
    private UserDao userMapper;


    @Override
    public DeviceDO getDev(UserDO userDO) {
        return deviceMapper.getDev(userDO);
    }

    @Override
    public DeviceDO get(int deviceId) {
        DeviceDO devide = deviceMapper.get(deviceId);
        return devide;
    }

    @Override
    public List<DeviceDO> list(Map<String, Object> map) {
        List<DeviceDO> list = deviceMapper.list(map);
        return list;
    }

    @Override
    public int count(Map<String, Object> map) {
        return deviceMapper.count(map);
    }

    @Override
    public int save(DeviceDO device) {
        UserDO user = userMapper.loadUser(device.getUserName());
        device.setDeptId(user.getDeptId().intValue());
        device.setUserId(user.getUserId().intValue());
        device.setCreateTime(new Date());
        int count = deviceMapper.save(device);
        return count;
    }

    @Override
    public int update(DeviceDO device) {
        UserDO user = userMapper.loadUser(device.getUserName());
        device.setDeptId(user.getDeptId().intValue());
        device.setUserId(user.getUserId().intValue());
        int r = deviceMapper.update(device);
        return r;
    }

    @Override
    public int remove(int deviceId) {
        return deviceMapper.remove(deviceId);
    }

    @Override
    public int batchRemove(int[] devIds) {
        int count = deviceMapper.batchRemove(devIds);
        return count;
    }
}