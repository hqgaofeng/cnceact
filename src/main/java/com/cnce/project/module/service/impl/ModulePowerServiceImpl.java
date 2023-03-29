package com.cnce.project.module.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cnce.project.module.dao.ModulePowerDao;
import com.cnce.project.module.domain.ModulePowerChangDO;
import com.cnce.project.module.domain.ModulePowerDO;
import com.cnce.project.module.domain.ModulePowerNewDO;
import com.cnce.project.module.service.ModulePowerService;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


@Service
public class ModulePowerServiceImpl implements ModulePowerService {

    @Autowired
    private ModulePowerDao modPowerMapper;

    @Override
    public int updateName(ModulePowerChangDO modulePowerChangDO) {
        return modPowerMapper.updateName(modulePowerChangDO);
    }

    @Override
    public ModulePowerDO get(int id) {
        return modPowerMapper.get(id);
    }

    @Override
    public List<ModulePowerNewDO> list(Map<String, Object> map) {
        List<ModulePowerNewDO> modulePower = modPowerMapper.list(map);
        return modulePower;
    }



    @Override
    public int count(Map<String, Object> map) {
        return modPowerMapper.count(map);
    }

    @Override
    public int save(ModulePowerDO modulePower) {
        modulePower.setCreateTime(new Date());
        return modPowerMapper.save(modulePower);
    }

    @Override
    public int update(ModulePowerDO modulePower) {
        modulePower.setCreateTime(new Date());
        return modPowerMapper.update(modulePower);
    }

    @Override
    public int remove(int id) {
        return modPowerMapper.remove(id);
    }

    @Override
    public int batchRemove(int[] ids) {
        return modPowerMapper.batchRemove(ids);
    }

    /**
     * @author shichenglong
     * @date 8/3/2023
     * return 查询所有姓名
     */
    @Override
    public List<String> findAllName() {
        return modPowerMapper.findAllName();
    }


    /**
     *
     * @author shichenglong
     * @date 10/3/2023
     * return 查询所有项目名称
     */

    @Override
    public List<String> findAllpName() {
        return modPowerMapper.findAllpName();
    }

    /**
     *
     * @author shichenglong
     * @date 10/3/2023
     * return 查询所有模块名称
     */
    @Override
    public List<String> findAllMod_name() {
        return modPowerMapper.findAllMod_name();
    }






}
