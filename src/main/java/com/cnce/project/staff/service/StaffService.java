package com.cnce.project.staff.service;

import com.cnce.project.staff.domain.StaffDO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public interface StaffService {
    StaffDO get(int id);

    List<StaffDO> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    int save (StaffDO staff);

    int update(StaffDO staff);

    int remove(int id);

    int batchRemove(int[] ids);

    boolean batchImportStaff(List<Map> list);

    List<String> loadStaff();

    StaffDO getUserByName(String staffName);

}
