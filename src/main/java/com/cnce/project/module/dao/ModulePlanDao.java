package com.cnce.project.module.dao;

import com.cnce.project.module.domain.ModulePlanDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ModulePlanDao {

    int save(ModulePlanDO moduleDO);

    int update(ModulePlanDO moduleDO);

    ModulePlanDO getPlanByModuleId(@Param("mId") int mId, @Param("planName") String planName);

}

