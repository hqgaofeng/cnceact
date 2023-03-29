package com.cnce.common.task.project;

import com.cnce.common.utils.PGSQLUtil;
import com.cnce.project.template.dao.TempSyncDao;
import com.cnce.project.template.domain.TempSyncDO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
public class ProjectSync {

    @Resource
    private TempSyncDao tempMapper;

    // 每天凌晨2点执行
    // @Scheduled(cron = "0 0/3 * * * ?")
    @Scheduled(cron = "0 0 2 * * ?")
    public void saveData(){
        System.out.println("=============项目信息同步开始============");
        Map<String, List<TempSyncDO>> map = this.groupProjectData();
        List<TempSyncDO> upd1List = map.get("upd1List");
        List<TempSyncDO> upd0List = map.get("upd0List");
        List<TempSyncDO> addList = map.get("addList");
        // 更新到禁用的项目
        if(upd1List.size()>0){
            tempMapper.updToNData(upd1List);
        }
        // 更新到正常的项目
        if(upd0List.size()>0){
            tempMapper.updToYData(upd0List);
        }
        // 插入新项目数据
        if(addList.size()>0){
            tempMapper.saveData(addList);
        }
    }

    // 项目数据分组
    private Map<String, List<TempSyncDO>> groupProjectData(){
        Map<String, List<TempSyncDO>> map = new HashMap<>();
        // 获取查询的jira数据
        List<TempSyncDO> jraList = PGSQLUtil.loadProjectData();
        // 获取平台库的项目数据
        List<TempSyncDO> proList = tempMapper.getProjectList(new HashMap<>());
        // 定义需要更新状态的TMP List
        List<TempSyncDO> upd1List = new ArrayList<>();
        List<TempSyncDO> upd0List = new ArrayList<>();
        List<TempSyncDO> nameList = new ArrayList<>();
        List<TempSyncDO> regionList = new ArrayList<>();
        // 1.当前表中检查是否存在这个项目
        // 2.若有此项目，则检查状态是否需要更新
        Iterator<TempSyncDO> iterator = jraList.iterator();
        while (iterator.hasNext()){
            // 当前jira库的项目名
            TempSyncDO td = iterator.next();
            boolean removeFlag = false;
            int jiraId = td.getJiraId();
            String jiraName = td.getpName();
            String jiraRegion = td.getRegion();
            for(int j=0; j<proList.size(); j++){
                // 当前平台库的项目名
                int pJiraId = proList.get(j).getJiraId();
                String proName = proList.get(j).getpName();
                String region = proList.get(j).getRegion();
                if(pJiraId == jiraId){
                    // 检查项目名称是否相同，相同则跳过，不相同选择更新
                    if(!proName.equals(jiraName)){
                        TempSyncDO temp = new TempSyncDO();
                        temp.setProId(proList.get(j).getProId());
                        temp.setpName(jiraName);
                        nameList.add(temp);
                    }
                    if(!jiraRegion.equals(region)){
                        TempSyncDO temp = new TempSyncDO();
                        temp.setProId(proList.get(j).getProId());
                        temp.setRegion(jiraRegion);
                        regionList.add(temp);
                    }
                    // 检查状态是否需要更新
                    if(td.getpStatus() ==0){
                        if(proList.get(j).getpStatus()==1){
                            upd1List.add(proList.get(j));
                        }
                    }
                    if(td.getpStatus() ==1){
                        if(proList.get(j).getpStatus()==0){
                            upd0List.add(proList.get(j));
                        }
                    }
                    // 检查
                    // 从jira list中移除相同的项
                    iterator.remove();
                    removeFlag=true;
                    break;
                }
            }
        }
        // 检查需要更新的项目
        if(nameList.size() > 0){
            this.updProjects(nameList);
        }
        if(regionList.size() > 0){
            this.updProjects(regionList);
        }
        map.put("upd1List", upd1List);
        map.put("upd0List", upd0List);
        map.put("addList", jraList);
        return map;
    }


    private void updProjects(List<TempSyncDO> list){
         for(TempSyncDO temp : list){
             // 更新项目信息
             tempMapper.update(temp);
         }
    }

}
