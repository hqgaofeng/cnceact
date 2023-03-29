package com.cnce.manager.profit.dao;

import com.cnce.manager.profit.domain.ProfitDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProfitDao {

    List<ProfitDO> getProfit(Integer toolId);

    List<ProfitDO> list(Map<String, Object> map);

    int save(ProfitDO profit);

    int insertList(@Param(value = "list") List<ProfitDO> list);

}
