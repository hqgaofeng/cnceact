package com.cnce.project.module.dao;

import com.cnce.project.module.domain.ModuleDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ModuleDao {

    ModuleDO get(int id);

    ModuleDO getModuleByName(@Param("modName") String modName);

    List<ModuleDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(ModuleDO moduleDO);

    int update(ModuleDO moduleDO);

    int remove(int id);

    int batchRemove(int[] ids);

}

