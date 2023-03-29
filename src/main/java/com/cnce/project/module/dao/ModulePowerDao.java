package com.cnce.project.module.dao;

import com.cnce.project.manpower.domain.EstimateDO;
import com.cnce.project.module.domain.ModulePowerChangDO;
import com.cnce.project.module.domain.ModulePowerDO;
import com.cnce.project.module.domain.ModulePowerNewDO;
import com.cnce.project.module.domain.ModulePowerSaveDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Mapper
public interface ModulePowerDao {


    String getModPowerByMeId(int id);
    int updateModPower(ModulePowerSaveDO modulePowerSaveDO);
    //***************


    int updateName(ModulePowerChangDO modulePowerChangDO);

    int saveDataChangeManpower(ModulePowerChangDO modulePowerChangDO);

    int saveDataPlanManpower(ModulePowerChangDO modulePowerChangDO);

    ModulePowerDO get(int id);

    List<ModulePowerNewDO> list(Map<String, Object> map);


    List<ModulePowerDO> list2();

    int count(Map<String, Object> map);

    int save(ModulePowerDO modulePower);

    int update(ModulePowerDO modulePower);

    int remove(int id);

    int batchRemove(int[] ids);

    List<String> findAllName();


    List<String> findAllpName();


    List<String> findAllMod_name();


}

