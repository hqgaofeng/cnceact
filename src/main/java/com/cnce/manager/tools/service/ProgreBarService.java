package com.cnce.manager.tools.service;


import com.cnce.system.domain.UserDO;

public interface ProgreBarService {

    boolean buildStart(Integer toolId);

    int getProgress(Integer toolId, String date, UserDO user);

    boolean checkNodes(UserDO user);

}
