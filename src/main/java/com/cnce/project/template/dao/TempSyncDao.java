package com.cnce.project.template.dao;

import com.cnce.project.template.domain.TempSyncDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TempSyncDao {

    int update(TempSyncDO tempSyncDO);

    List<TempSyncDO> getProjectList(Map<String, String> map);

    void updToNData(@Param("list") List<TempSyncDO> list);

    void updToYData(@Param("list") List<TempSyncDO> list);

    void saveData(@Param("list") List<TempSyncDO> list);

    TempSyncDO getProjectByName(@Param("pName") String pName, @Param("region") String region);

    TempSyncDO getProjectByJId(@Param("JiraId") int JiraId);

    List<String> getProjects(@Param("region") String region);

}

