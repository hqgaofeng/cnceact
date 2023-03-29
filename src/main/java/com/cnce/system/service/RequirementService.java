package com.cnce.system.service;

import com.cnce.system.domain.RequirementDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface RequirementService {

	RequirementDO get(int reqId);

	List<RequirementDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(RequirementDO require);

	int update(RequirementDO require);

	int remove(int reqId);

	int batchRemove(int[] reqIds);

}
