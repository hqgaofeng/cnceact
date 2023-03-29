package com.cnce.project.business.dao;

import com.cnce.project.business.domain.BaselineDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BaselineDao {

    BaselineDO get(int id);

    List<BaselineDO> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    int save (BaselineDO baseline);

    int update(BaselineDO baseline);

    int remove(int id);

    int batchRemove(int[] ids);

    boolean batchImportBaseline(List<BaselineDO> list);

    List<String> loadModules();

    BaselineDO getModuleByName(@Param("module") String module);

}

