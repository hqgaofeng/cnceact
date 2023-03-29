package com.cnce.project.manpower.service.impl;

import com.cnce.project.manpower.dao.ProjectUserDao;
import com.cnce.project.manpower.domain.ProjectUserDO;
import com.cnce.project.manpower.service.ProjectUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProjectUserServiceImple implements ProjectUserService {

    @Autowired
    ProjectUserDao UserMapper;


    @Override
    public ProjectUserDO get(String id) {
        return UserMapper.get(id);
    }

    @Override
    public List<ProjectUserDO> list(Map<String, Object> map) {
        return UserMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return UserMapper.count(map);
    }

    @Override
    public int update(ProjectUserDO projectUser) {
        return UserMapper.update(projectUser);
    }

    @Override
    public int save(ProjectUserDO projectUser) {
        return UserMapper.save(projectUser);
    }

    @Override
    public List<ProjectUserDO> getDepartment(String region) {
        String format;
        if("XA".equals(region)){
            format = "西安";
        }else {
            format = "北京";
        }
        List<String> noShowList = noShowForDepartment();
        List<ProjectUserDO> list =  UserMapper.getDepartment(format);
        List<ProjectUserDO> newList = new ArrayList<>();
        for(ProjectUserDO pu : list){
            if(!noShowList.contains(pu.getDepartment())){
                newList.add(pu);
            }
        }
        return newList;
    }

    @Override
    public List<ProjectUserDO> getDomain(String region, String department) {
        String format;
        if("XA".equals(region)){
            format = "西安";
        }else {
            format = "北京";
        }
        List<String> noShowList = noShowForDomain();
        List<ProjectUserDO> list = UserMapper.getDomain(format, department);
        List<ProjectUserDO> domainList = new ArrayList<>();
        for(ProjectUserDO pu : list){
            if(!noShowList.contains(pu.getDomain())){
                domainList.add(pu);
            }
        }
        return domainList;
    }

    // 一级部门不需要显示的部门列表
    private List<String> noShowForDepartment(){
        List<String> list = new ArrayList<>();
        list.add("EMS管理部");
        list.add("人力资源部");
        list.add("销售三部");
        list.add("产品市场部");
        list.add("交付一部");
        list.add("总经办");
        list.add("IT部");
        list.add("财务部");
        list.add("商务一部");
        list.add("商务二部");
        list.add("行政部");
        list.add("商务部");
        list.add("交付部");
        list.add("HMD PDT部");
        list.add("荣耀PDT部");
        list.add("业务拓展部");
        list.add("HMD部");
        list.add("H1/运营商PDT部");
        list.add("销售部");
        list.add("MOTO PDT部");
        list.add("市场部");
        list.add("None");
        list.add("BU3-XA财务部");
        list.add("BU3-DG质量部");
        list.add("BU1-SZ结构部");
        list.add("BU1-SZ软件部");
        list.add("BU3-EMS管理部");
        list.add("BU1-SZ项目部");
        list.add("BU1-SZ测试部");
        list.add("BU3-EMS管理部");
        list.add("XA商务二部");
        list.add("XA交付一部");
        list.add("BU1-SZ硬件部");
        list.add("营销中心");
        list.add("XA商务一部");
        list.add("BU1-SZ精益生产部");
        list.add("BU3-DG商务部");
        list.add("XA销售三部");
        list.add("BU3-DG商务部");
        list.add("DQA二部");
        list.add("器件专家组");
        return list;
    }

    // 二级部门不需要显示的部门列表
    private List<String> noShowForDomain(){
        List<String> domainList = new ArrayList<>();
        domainList.add("软测实习生");
        domainList.add("TSE");
        domainList.add("None");
        domainList.add("实习");
        domainList.add("包装");
        domainList.add("HR");
        domainList.add("软测");
        domainList.add("结构");
        domainList.add("硬工");
        domainList.add("硬测");
        domainList.add("未知领域");
        domainList.add("camera");
        domainList.add("相机");
        domainList.add("生产测试");
        domainList.add("IT");
        domainList.add("商务");
        domainList.add("测试");
        domainList.add("软件实习");
        domainList.add("PCB Layout");
        domainList.add("硬件");
        domainList.add("L&D");
        domainList.add("结构实习");
        domainList.add("测试实习");
        domainList.add("财务");
        domainList.add("BT2013-3-22");
        domainList.add("市场");
        domainList.add("影像");
        domainList.add("行政");
        domainList.add("AE");
        domainList.add("测试代表");
        domainList.add("驻厂PM");
        domainList.add("实习生");
        domainList.add("软件");
        domainList.add("BU1");
        domainList.add("XA");
        domainList.add("器件");
//        domainList.add("硬测代表");
//        domainList.add("硬件测试");
//        domainList.add("DQA");
//        domainList.add("软件测试");
        return domainList;
    }
}
