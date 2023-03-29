package com.cnce.project.business.service.impl;

import com.cnce.project.business.dao.BaselineDao;
import com.cnce.project.business.domain.BaselineDO;
import com.cnce.project.business.service.BaselineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class BaselineServiceImpl implements BaselineService {

    @Autowired
    private BaselineDao baseMapper;


    @Override
    public BaselineDO get(int id) {
        return baseMapper.get(id);
    }

    @Override
    public List<BaselineDO> list(Map<String, Object> map) {
        return baseMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return baseMapper.count(map);
    }

    @Override
    public int save(BaselineDO baseline) {
        baseline.setCreateTime(new Date());
        return baseMapper.save(baseline);
    }

    @Override
    public int update(BaselineDO baseline) {
        baseline.setCreateTime(new Date());
        return baseMapper.update(baseline);
    }

    @Override
    public int remove(int id) {
        return baseMapper.remove(id);
    }

    @Override
    public int batchRemove(int[] ids) {
        return baseMapper.batchRemove(ids);
    }

    @Override
    public boolean batchImportBaseline(List<Map> list) {
        List<BaselineDO> baList = new ArrayList<>();
        if(list.size() > 0){
            list.remove(0);
            for(int i=0; i<list.size(); i++){
                BaselineDO bd = new BaselineDO();
                String scope = list.get(i).get("scope").toString();
                bd.setScope(scope);
                String module = list.get(i).get("module").toString();
                bd.setModule(module);
                String baseline = list.get(i).get("baseline").toString();
                bd.setBaseline(baseline);
                bd.setCreateTime(new Date());
                baList.add(bd);
            }
        }
        return baseMapper.batchImportBaseline(baList);
    }

    @Override
    public List<String> loadModules() {
        return baseMapper.loadModules();
    }
}