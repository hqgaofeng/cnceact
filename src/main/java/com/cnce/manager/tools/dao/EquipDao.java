package com.cnce.manager.tools.dao;

import com.cnce.manager.tools.domain.EquipDO;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EquipDao {

    List<EquipDO> getNameByDeptId(Integer deptId);


}
