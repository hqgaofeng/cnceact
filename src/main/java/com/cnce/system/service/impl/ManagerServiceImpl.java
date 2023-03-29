package com.cnce.system.service.impl;

import com.cnce.system.dao.ManagerDao;
import com.cnce.system.domain.ManagerDO;
import com.cnce.system.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.lang.System.*;


@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerDao managerMapper;

    @Override
    public List<ManagerDO> getToolsInfo(Map<String, Object> params) {
        List<ManagerDO> list = managerMapper.getToolsInfo(params);
        return list;
    }

    @Override
    public int count(Map<String, Object> params) {
        int count=managerMapper.count(params);
        return count;
    }

    @Override
    public ManagerDO get(Integer id) {
        ManagerDO managerDO=managerMapper.get(id);
        return managerDO;
    }

    @Override
    public int save(ManagerDO manager) {
        String useDept="";
        String name="";
        Field[] fs = manager.getClass().getDeclaredFields();
            for (Field field : fs) {
                field.setAccessible(true);
                if (field.toString().contains("useDept")) {
                    try {
                        useDept = field.get(manager).toString();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                if (field.toString().contains("name")) {
                    try {
                        name = field.get(manager).toString();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        Integer deptId=managerMapper.getDeptIdByName(useDept);
        Integer reqId=managerMapper.getReqIdByToolName(name);
        manager.setUpdateTime(new Date());
        manager.setDeptId(deptId);
        manager.setReqId(reqId);
        manager.setToolPath("/home/cnce/tool");
        return managerMapper.save(manager);
    }

    @Override
    public int update(ManagerDO manager) {
        return managerMapper.update(manager);
    }

    @Override
    public int remove(Integer id) {
        return managerMapper.remove(id);
    }

    @Override
    public int batchRemove(int[] pids) {
        return managerMapper.batchRemove(pids);
    }
}
