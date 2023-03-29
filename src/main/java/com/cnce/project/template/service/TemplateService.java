package com.cnce.project.template.service;

import com.cnce.project.template.domain.TemplateDO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface TemplateService {
    TemplateDO get(int id);

    List<TemplateDO> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    int save (TemplateDO template);

    int update(TemplateDO template);

    boolean isRev(int id);

    int remove(int id);

    int batchRemove(int[] ids);

    int removal(int id);

    List<String> loadMonth();

    List<String> loadProject(String region);

    String getMsg(int id);
}
