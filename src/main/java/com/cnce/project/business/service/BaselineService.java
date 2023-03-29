package com.cnce.project.business.service;

import com.cnce.project.business.domain.BaselineDO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public interface BaselineService {
    BaselineDO get(int id);

    List<BaselineDO> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    int save (BaselineDO baseline);

    int update(BaselineDO baseline);

    int remove(int id);

    int batchRemove(int[] ids);

    boolean batchImportBaseline(List<Map> list);

    List<String> loadModules();
}
