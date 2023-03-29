package com.cnce.project.module.service.impl;

import com.cnce.project.module.dao.ModuleDao;
import com.cnce.project.module.domain.ModuleDO;
import com.cnce.project.module.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleDao moduleMapper;


    @Override
    public ModuleDO get(int id) {
        return moduleMapper.get(id);
    }

    @Override
    public List<ModuleDO> list(Map<String, Object> map) {
        return moduleMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return moduleMapper.count(map);
    }

    @Override
    public int save(ModuleDO moduleDO) {
        return moduleMapper.save(moduleDO);
    }

    @Override
    public int update(ModuleDO moduleDO) {
        return moduleMapper.update(moduleDO);
    }

    @Override
    public int remove(int id) {
        return moduleMapper.remove(id);
    }

    @Override
    public int batchRemove(int[] ids) {
        return moduleMapper.batchRemove(ids);
    }
}