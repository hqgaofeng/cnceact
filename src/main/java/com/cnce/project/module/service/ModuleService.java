package com.cnce.project.module.service;

import com.cnce.project.module.domain.ModuleDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ModuleService {
    ModuleDO get(int id);

    List<ModuleDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(ModuleDO moduleDO);

    int update(ModuleDO moduleDO);

    int remove(int id);

    int batchRemove(int[] ids);

}
