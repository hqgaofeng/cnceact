package com.cnce.project.manpower.dao;

import com.cnce.project.manpower.domain.EstimateDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper
public interface EstimateDao {

    EstimateDO get(int id);

    List<EstimateDO> list(Map<String, Object> map);

    int save(EstimateDO estimateDO);

    int update(EstimateDO estimateDO);

    int updEstimate(EstimateDO estimateDO);

    int remove(int id);

    EstimateDO getEstimateByMonth(@Param("effectMonth") String effMonth, @Param("userId") int userId);

    List<EstimateDO> queryList(@Param(value = "list") List<EstimateDO> list);

}
