package com.cnce.manager.require.service.impl;

import com.cnce.manager.require.dao.RequireDao;
import com.cnce.manager.require.domain.RequireDO;
import com.cnce.manager.require.service.RequireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RequireServiceImpl implements RequireService {

    @Autowired
    private RequireDao requireMapper;


    @Override
    public RequireDO get(int id) {
        return requireMapper.get(id);
    }

    @Override
    public List<RequireDO> list(Map<String, Object> map) {
        List<RequireDO> list = requireMapper.list(map);
        return list;
    }

    @Override
    public int count(Map<String, Object> map) {
        return requireMapper.count(map);
    }

    @Override
    public int save(RequireDO require) {
        require.setCreateTime(new Date());
        return requireMapper.save(require);
    }

    @Override
    public int update(RequireDO require) {
        require.setCreateTime(new Date());
        return requireMapper.update(require);
    }
}
