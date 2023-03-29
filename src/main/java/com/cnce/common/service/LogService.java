package com.cnce.common.service;


import org.springframework.stereotype.Service;

import com.cnce.common.domain.LogDO;
import com.cnce.common.domain.PageDO;
import com.cnce.common.utils.Query;
@Service
public interface LogService {
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
