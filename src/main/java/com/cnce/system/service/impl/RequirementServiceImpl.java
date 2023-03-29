package com.cnce.system.service.impl;

import com.cnce.system.dao.RequirementDao;
import com.cnce.system.domain.RequirementDO;
import com.cnce.system.service.RequirementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class RequirementServiceImpl implements RequirementService {

	@Autowired
	RequirementDao reqMapper;

	private static final Logger logger = LoggerFactory.getLogger(RequirementService.class);

	@Override
	public RequirementDO get(int id) {
		RequirementDO require = reqMapper.get(id);
		return require;
	}

	@Override
	public List<RequirementDO> list(Map<String, Object> map) {
		List<RequirementDO> list = reqMapper.list(map);
		return list;
	}

	@Override
	public int count(Map<String, Object> map) {
		return reqMapper.count(map);
	}

	@Transactional
	@Override
	public int save(RequirementDO requireDO) {
		requireDO.setStatus(1);
		requireDO.setCreateTime(new Date());
		int count = reqMapper.save(requireDO);
		return count;
	}

	@Override
	public int update(RequirementDO requireDO) {
		requireDO.setCreateTime(new Date());
		int r = reqMapper.update(requireDO);
		return r;
	}

	@Override
	public int remove(int reqId) {
		return reqMapper.remove(reqId);
	}

	@Override
	public int batchRemove(int[] reqIds) {
		int count = reqMapper.batchRemove(reqIds);
		return count;
	}

}
