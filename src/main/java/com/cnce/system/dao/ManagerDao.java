package com.cnce.system.dao;

import com.cnce.system.domain.ManagerDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ManagerDao {

    List<ManagerDO> getToolsInfo(Map<String, Object> params);
    
    int count(Map<String, Object> params);

    ManagerDO get(Integer id);

    int save(ManagerDO manager);

    Integer getDeptIdByName(String deptName);

    Integer getReqIdByToolName(String toolName);

    int update(ManagerDO manager);

    int remove(Integer id);

    int  batchRemove(int[] pids);

}
