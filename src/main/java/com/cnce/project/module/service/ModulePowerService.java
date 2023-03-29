package com.cnce.project.module.service;

import com.cnce.project.module.domain.ModulePowerChangDO;
import com.cnce.project.module.domain.ModulePowerDO;
import com.cnce.project.module.domain.ModulePowerNewDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ModulePowerService {

    int updateName(ModulePowerChangDO modulePowerChangDO);


    ModulePowerDO get(int id);

    List<ModulePowerNewDO> list(Map<String, Object> map);



    int count(Map<String, Object> map);

    int save(ModulePowerDO modulePower);

    int update(ModulePowerDO modulePower);

    int remove(int id);

    int batchRemove(int[] ids);

    List<String> findAllName();

    List<String> findAllpName();

    List<String> findAllMod_name();

}
