package com.cnce.project.staff.dao;

import com.cnce.project.staff.domain.StaffDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StaffDao {

    StaffDO get(int id);

    List<StaffDO> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    int save (StaffDO staff);

    int update(StaffDO staff);

    int remove(int id);

    int batchRemove(int[] ids);

    boolean batchImportStaff(@Param("list") List<StaffDO> list);

    List<String> loadStaff();

    StaffDO getUserByName(String staffName);

}

