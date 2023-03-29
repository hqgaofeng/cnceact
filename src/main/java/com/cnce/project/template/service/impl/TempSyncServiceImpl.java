package com.cnce.project.template.service.impl;

import com.cnce.project.template.dao.TempSyncDao;
import com.cnce.project.template.service.TempSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class TempSyncServiceImpl implements TempSyncService {

    @Autowired
    private TempSyncDao tmpSyncMapper;


    @Override
    public List<String> getProjects(String region) {
        return tmpSyncMapper.getProjects(region);
    }
}