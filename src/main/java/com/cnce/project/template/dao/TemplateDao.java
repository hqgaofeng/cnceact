package com.cnce.project.template.dao;

import com.cnce.project.template.domain.TemplateDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TemplateDao {
    String getProjectByName(int jiraId);

    TemplateDO get(int id);

    List<TemplateDO> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    int save (TemplateDO template);

    int update(TemplateDO template);

    int remove(int id);

    int batchRemove(int[] ids);

    int removal(int id);

    List<String> loadMonth();

    List<String> loadProject(@Param("region") String region);

    List<TemplateDO> getProjectList(Map<String, Object> map);

    TemplateDO getTemplate(TemplateDO template);

}

