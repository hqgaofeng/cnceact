package com.cnce.common.task.project;

import com.cnce.common.utils.PGSQLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * 部门信息同步
 */

@Component
public class DeptSync {

//    @Autowired
//    private ProjectDeptDao deptMapper;

    private String sql = "WITH department AS ( SELECT customfieldvalue.issue, customfieldvalue.stringvalue FROM customfieldvalue WHERE customfieldvalue.customfield = 11702 ),\n" +
            "user_domain AS ( SELECT customfieldvalue.issue, customfieldvalue.stringvalue FROM customfieldvalue WHERE customfieldvalue.customfield = 11703 ),\n" +
            "region AS ( SELECT customfieldvalue.issue, customfieldvalue.stringvalue FROM customfieldvalue WHERE customfieldvalue.customfield = 13301 ),\n" +
            "user_info AS (\n" +
            "\tSELECT\n" +
            "\t\tdepartment.stringvalue AS dept,\n" +
            "\t\tuser_domain.stringvalue AS DOMAIN,\n" +
            "\t\tregion.stringvalue AS region \n" +
            "\tFROM\n" +
            "\t\tjiraissue\n" +
            "\t\tLEFT JOIN department ON department.issue = jiraissue.\n" +
            "\t\tID LEFT JOIN user_domain ON user_domain.issue = jiraissue.\n" +
            "\t\tID LEFT JOIN region ON region.issue = jiraissue.ID \n" +
            "\tWHERE\n" +
            "\t\tjiraissue.project IN ( 11600, 12502 ) \n" +
            "\t) SELECT\n" +
            "\tregion,\n" +
            "\tdept,\n" +
            "\t\"domain\" \n" +
            "FROM\n" +
            "\tuser_info \n" +
            "GROUP BY\n" +
            "\tregion,\n" +
            "\tdept,\n" +
            "\t\"domain\"";

//     @Scheduled(cron = "0 0/3 * * * ?")
    // 每天凌晨0点执行
//     @Scheduled(cron = "0 0 0 */1 * ?")
//    public void doTask(){
//        System.out.println("=============部门信息同步开始============");
//        List<HashMap<String, Object>> deptInfo = PGSQLUtil.loadUserData(sql);
//        //批量添加的list
//        List<ProjectDeptDO> addList = new ArrayList<>();
//        for(HashMap<String, Object> m : deptInfo){
//            // 地域 XA/BJ
//            String region = m.get("region").toString();
//            // 一级部门
//            String deptName = m.get("dept").toString();
//            // 二级部门
//            String domain = m.get("domain").toString();
//
//            int dId = 1;
//            if("XA".equals(region)){
//                dId = 1;
//            }else {
//                dId = 2;
//            }
//
//            // 先检查一级部门名称是否在不入库列表
//            if(!noneSaveForDept1List().contains(deptName)){
//                // 平台库地域名称
//                ProjectDeptDO deptDO = new ProjectDeptDO();
//                deptDO.setParentId(dId);
//                deptDO.setDeptName(deptName);
//                // 检查一级部门
//                List<ProjectDeptDO> deptList = deptMapper.getDept2(deptDO);
//                if(deptList.size()>0){
//                    // 先检查二级部门名称是否在不入库列表
//                    if(!noneSaveForDept2List().contains(domain)){
//                        // 一级部门存在则检查二级部门
//                        int parentId = deptList.get(0).getDeptId();
//                        deptDO.setParentId(parentId);
//                        deptDO.setDeptName(domain);
//                        // 检查二级部门
//                        List<ProjectDeptDO> dept2List = deptMapper.getDept2(deptDO);
//                        if(dept2List.size() == 0){
//                            // 二级部门不存在，则添加进add list
//                            ProjectDeptDO dept = new ProjectDeptDO();
//                            dept.setParentId(parentId);
//                            dept.setDeptName(domain);
//                            addList.add(dept);
//                        }
//                    }
//                }else {
//                    // 平台库中没有的一级部门，添加进list
//                    ProjectDeptDO dept = new ProjectDeptDO();
//                    dept.setParentId(dId);
//                    dept.setDeptName(deptName);
//                    addList.add(dept);
//                }
//            }
//        }
//        // 把检查没有的部门添加进平台库
//        if(addList.size()>0){
//            deptMapper.batchSave(addList);
//        }
//    }

    // 一级部门不需要入库的jira部门列表
    private List<String> noneSaveForDept1List(){
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
        return list;
    }

    // 二级部门不需要入库的jira部门列表
    private List<String> noneSaveForDept2List(){
        List<String> list = new ArrayList<>();
        list.add("软测实习生");
        list.add("TSE");
        list.add("None");
        list.add("实习");
        list.add("包装");
        list.add("HR");
        list.add("软测");
        list.add("结构");
        list.add("硬工");
        list.add("硬测");
        list.add("未知领域");
        list.add("camera");
        list.add("相机");
        list.add("生产测试");
        list.add("IT");
        list.add("商务");
        list.add("测试");
        list.add("软件实习");
        list.add("PCB Layout");
        list.add("硬件");
        list.add("L&D");
        list.add("结构实习");
        list.add("测试实习");
        list.add("财务");
        list.add("BT2013-3-22");
        list.add("市场");
        list.add("影像");
        list.add("行政");
        list.add("AE");
        list.add("测试代表");
        list.add("驻厂PM");
        list.add("实习生");
        list.add("软件");
        list.add("BU1");
        list.add("XA");
        list.add("器件");
        list.add("硬测代表");
        list.add("硬件测试");
        list.add("DQA");
        list.add("软件测试");
        return list;
    }


}
