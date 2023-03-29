package com.cnce.system.dao;

import com.cnce.system.domain.RequirementDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RequirementDao {

	RequirementDO get(int reqId);

	List<RequirementDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(RequirementDO require);

	int update(RequirementDO require);

	int remove(int reqId);

	int batchRemove(int[] reqIds);

}
