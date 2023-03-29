package com.cnce.manager.tools.service.impl;

import com.cnce.manager.tools.dao.EquipDao;
import com.cnce.manager.tools.domain.EquipDO;
import com.cnce.manager.tools.service.EquipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EquipServiceImpl implements EquipService {

    @Autowired
    EquipDao equipMapper;

    @Override
    public List<String> getEquipName(Integer deptId){
        List<String> listComputerAlias = new ArrayList<String>();
        List<EquipDO> reList= equipMapper.getNameByDeptId(deptId);
        for (int i=0;i<reList.size();i++){
            String valueName = reList.get(i).getComputerAlias();
            listComputerAlias.add(valueName);
        }
        return listComputerAlias;
    }

    @Override
    public List<String> getEquipIp(Integer deptId){
        List<String> listIp = new ArrayList<String>();
        List<EquipDO> reList= equipMapper.getNameByDeptId(deptId);
        for (int i=0;i<reList.size();i++){
            String valueName = reList.get(i).getComputerIp();
            listIp.add(valueName);
        }
        return listIp;
    }
}
