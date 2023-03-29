package com.cnce.project.project.service.impl;

import com.cnce.project.project.dao.ProjectDao;
import com.cnce.project.project.domain.ProjectDO;
import com.cnce.project.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao proMapper;


    @Override
    public ProjectDO get(int id) {
        return proMapper.get(id);
    }

    @Override
    public List<ProjectDO> list(Map<String, Object> map) {
        return proMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return proMapper.count(map);
    }

    @Override
    public int remove(int id) {
        return proMapper.remove(id);
    }

    @Override
    public int batchRemove(int[] ids) {
        return proMapper.batchRemove(ids);
    }

    @Override
    public boolean batchImportProjects(List<Map> list) {
        List<ProjectDO> proList = new ArrayList<>();
        if(list.size() > 0){
            list.remove(0);
            for(int i=0; i<list.size(); i++){
                ProjectDO pd = new ProjectDO();
                String proName = list.get(i).get("proName").toString();
                pd.setProName(proName);
                proList.add(pd);
            }
        }
        return proMapper.batchImportProjects(proList);
    }

    @Override
    public List<String> loadProject() {
        return proMapper.loadProject();
    }

    @Override
    public int update(ProjectDO project) {
        project.setCreateTime(new Date());
        return proMapper.update(project);
    }

    @Override
    public int save(ProjectDO project) {
        project.setCreateTime(new Date());
        return proMapper.save(project);
    }
}