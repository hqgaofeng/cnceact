package com.cnce.manager.tools.service;


import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface WifiSignalService {

    boolean beginTest(Map<Object, Object> params);

    boolean cancelTask(String equipName);
}
