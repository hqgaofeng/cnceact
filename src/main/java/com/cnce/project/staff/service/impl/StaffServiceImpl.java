package com.cnce.project.staff.service.impl;

import com.cnce.project.staff.dao.StaffDao;
import com.cnce.project.staff.domain.StaffDO;
import com.cnce.project.staff.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffDao staffMapper;


    @Override
    public StaffDO get(int id) {
        return staffMapper.get(id);
    }

    @Override
    public List<StaffDO> list(Map<String, Object> map) {
        return staffMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return staffMapper.count(map);
    }

    @Override
    public int save(StaffDO staff) {
        staff.setCreateTime(new Date());
        return staffMapper.save(staff);
    }

    @Override
    public int update(StaffDO staff) {
        staff.setCreateTime(new Date());
        return staffMapper.update(staff);
    }

    @Override
    public int remove(int id) {
        return staffMapper.remove(id);
    }

    @Override
    public int batchRemove(int[] ids) {
        return staffMapper.batchRemove(ids);
    }

    @Override
    public boolean batchImportStaff(List<Map> list) {
        List<StaffDO> staffList = new ArrayList<>();
        if(list.size() > 0){
            list.remove(0);
            for(int i=0; i<list.size(); i++){
                StaffDO sd = new StaffDO();
                String staffName = list.get(i).get("staffName").toString();
                sd.setStaffName(staffName);
                String group = list.get(i).get("sGroup").toString();
                sd.setsGroup(group);
                String propTxt = list.get(i).get("property").toString();
                int property = propTxt.equals("正式") ? 1 : 0;
                sd.setProperty(property);
                String jobContent = list.get(i).get("jobContent").toString();
                sd.setJobContent(jobContent);
                String experience = list.get(i).get("experience").toString();
                sd.setExperience(experience);
                sd.setCreateTime(new Date());
                staffList.add(sd);
            }
        }
        return staffMapper.batchImportStaff(staffList);
    }

    @Override
    public List<String> loadStaff() {
        return staffMapper.loadStaff();
    }

    @Override
    public StaffDO getUserByName(String staffName) {
        return staffMapper.getUserByName(staffName);
    }
}