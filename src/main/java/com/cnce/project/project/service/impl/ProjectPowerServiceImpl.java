package com.cnce.project.project.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnce.project.project.dao.ProjectPowerDao;
import com.cnce.project.project.domain.ProjectPowerDO;
import com.cnce.project.project.service.ProjectPowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProjectPowerServiceImpl implements ProjectPowerService {

    @Autowired
    private ProjectPowerDao projectPowerMapper;

    @Override
    public List<ProjectPowerDO> list(Map<String, Object> map) {
        JSONObject jsonb = new JSONObject();
        List<ProjectPowerDO> projectPower = projectPowerMapper.list(map);
        for(ProjectPowerDO p : projectPower){
            jsonb.put("姓名", p.getStaffName());
            jsonb.put("人力", p.getActualManpower());
            p.setPlanManpower(jsonb.toString());
        }
        return projectPower;
    }

    @Override
    public int count(Map<String, Object> map) {
        return projectPowerMapper.count(map);
    }

    @Override
    public String loadTotalPower() {
        List<BigDecimal> list = new ArrayList<>();
        List<ProjectPowerDO> projectPower = projectPowerMapper.list(new HashMap<>());
        for(ProjectPowerDO p : projectPower){
            if(p.getTotalPower() != null){
                BigDecimal bd = new BigDecimal(p.getTotalPower());
                list.add(bd);
            }
        }
        BigDecimal totalPower = list.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalPower.toString();
    }
}