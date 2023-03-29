package com.cnce.project.module.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public interface ModulePlanService {
    // 查询模块计划
    List<Map<String, Object>> loadData(Map<String, Object> map);
    // 模块人力数据保存
    int saveDatas(Map<String, Object> map);

}
