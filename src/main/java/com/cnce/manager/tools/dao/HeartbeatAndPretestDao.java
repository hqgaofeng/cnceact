package com.cnce.manager.tools.dao;

import com.cnce.manager.tools.domain.HeartbeatAndPretestBlueDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 工具管理
 * @author Alan
 * @email chenfei917@163.com
 * @date 2017-10-03 15:35:39
 */
@Mapper
public interface HeartbeatAndPretestDao {

    List<HeartbeatAndPretestBlueDO> getAllAppInfo(Map<String, Object> map);

    int count(Map<String, Object> map);

}
