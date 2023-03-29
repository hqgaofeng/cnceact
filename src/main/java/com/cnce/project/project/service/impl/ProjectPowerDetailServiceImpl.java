package com.cnce.project.project.service.impl;

import com.cnce.project.project.dao.ProjectPowerDetailDao;
import com.cnce.project.project.domain.ProjectPowerDetailDO;
import com.cnce.project.project.service.ProjectPowerDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ProjectPowerDetailServiceImpl implements ProjectPowerDetailService {


    @Autowired
    private ProjectPowerDetailDao powerDetailMapper;

    @Override
    public List<ProjectPowerDetailDO> list(Map<String, Object> map) {
        List<ProjectPowerDetailDO> pdList = powerDetailMapper.list(map);
        for(ProjectPowerDetailDO pd : pdList){
            String work = pd.getModName() + ":" + pd.getStaffName() + " " + pd.getActualManpower();
            pd.setWorkContent(work);
        }
        return pdList;
    }

    @Override
    public int count(Map<String, Object> map) {
        return powerDetailMapper.count(map);
    }
}