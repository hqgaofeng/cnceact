package com.cnce.project.template.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TempSyncService {

    List<String> getProjects(@Param("region") String region);
}
