package com.cnce.project.project.service.impl;

import com.cnce.project.project.dao.ProjectSummaryDao;
import com.cnce.project.project.domain.ProjectSummaryDO;
import com.cnce.project.project.service.ProjectSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class ProjectSummaryServiceImpl implements ProjectSummaryService {

    @Autowired
    private ProjectSummaryDao summaryMapper;

    @Override
    public List<ProjectSummaryDO> list(Map<String, Object> map) {
        List<ProjectSummaryDO> summaryList = summaryMapper.list(map);
        for(ProjectSummaryDO sd : summaryList){
            BigDecimal totalPower = new BigDecimal(sd.getTotalPower());
            BigDecimal testCycle = new BigDecimal(sd.getTestCycle());
            BigDecimal actualPower = new BigDecimal(sd.getActualManpower());
            BigDecimal dailyPower = totalPower.divide(testCycle, BigDecimal.ROUND_HALF_UP);
            BigDecimal totalLackPower = totalPower.subtract(actualPower);
            BigDecimal dailyLackPower = dailyPower.subtract(actualPower);
            // 每日需求人力
            sd.setDailyReqPower(dailyPower.toString());
            //总欠缺人力
            sd.setTotalLackPower(totalLackPower.toString());
            //日欠缺人力
            sd.setDailyLackPower(dailyLackPower.toString());
        }
        return summaryList;
    }

    @Override
    public int count(Map<String, Object> map) {
        return summaryMapper.count(map);
    }

}