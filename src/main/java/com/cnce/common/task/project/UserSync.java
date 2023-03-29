package com.cnce.common.task.project;

import com.cnce.common.utils.PGSQLUtil;
import com.cnce.project.manpower.dao.ProjectUserDao;
import com.cnce.project.manpower.domain.ProjectUserDO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;

@Component
public class UserSync {

    @Resource
    private ProjectUserDao userMapper;

    String sql = "" +
            "WITH\n" +
            "userInfo AS (\n" +
            "\tSELECT \n" +
            "\t\tapp_user.user_key,\n" +
            "\t\tuser_name,\n" +
            "\t\tcwd_user.display_name \n" +
            "\tFROM \n" +
            "\t\tcwd_user \n" +
            "\tJOIN app_user ON app_user.lower_user_name = cwd_user.lower_user_name\n" +
            "),\n" +
            "--11704 user.loginname\n" +
            "LoginName as (\n" +
            "\tSELECT \n" +
            "\t\tissue,\n" +
            "\t\tuserInfo.user_name,\n" +
            "\t\tuserInfo.display_name\n" +
            "\tfrom\n" +
            "\t\tcustomfieldvalue\n" +
            "\tLEFT JOIN userInfo on userInfo.user_key = customfieldvalue.stringvalue\n" +
            "\twhere customfield = 11704\n" +
            "),\n" +
            "--user.name 11705\n" +
            "UserName as (\n" +
            "\tSELECT\n" +
            "\t\tissue,\n" +
            "\t\tstringvalue\n" +
            "\tfrom customfieldvalue\n" +
            "\twhere customfield = 11705\n" +
            "),\n" +
            "--user.department 11702\n" +
            "Dept as (\n" +
            "\tSELECT\n" +
            "\t\tissue,\n" +
            "\t\tstringvalue\n" +
            "\tfrom customfieldvalue\n" +
            "\twhere customfield = 11702\n" +
            "),\n" +
            "--user.status 11706\n" +
            "Status AS (\n" +
            "\t\tSELECT \n" +
            "\t\t\tcustomfieldvalue.issue,\n" +
            "\t\t\tcustomfieldoption.customvalue \n" +
            "\t\tFROM \n" +
            "\t\t\tcustomfieldvalue \n" +
            "\t\tJOIN customfieldoption ON customfieldvalue.stringvalue = cast(customfieldoption.id AS VARCHAR) \n" +
            "\t\tWHERE \n" +
            "\t\t\tcustomfieldvalue.customfield = 11706\n" +
            "),\n" +
            "--email 11707\n" +
            "Email as (\n" +
            "\tSELECT\n" +
            "\t\tissue,\n" +
            "\t\tstringvalue\n" +
            "\tfrom customfieldvalue\n" +
            "\twhere customfield = 11707\n" +
            "),\n" +
            "--user.domain 11703\n" +
            "MainDept as (\n" +
            "\tSELECT\n" +
            "\t\tissue,\n" +
            "\t\tstringvalue\n" +
            "\tfrom customfieldvalue\n" +
            "\twhere customfield = 11703\n" +
            "),\n" +
            "--user.region 13301\n" +
            "region as (\n" +
            "\tSELECT\n" +
            "\t\tissue,\n" +
            "\t\tstringvalue\n" +
            "\tfrom customfieldvalue\n" +
            "\twhere customfield = 13301\n" +
            "),\n" +
            "--user.code 11708\n" +
            "Code as (\n" +
            "\tSELECT\n" +
            "\t\tissue,\n" +
            "\t\tstringvalue\n" +
            "\tfrom customfieldvalue\n" +
            "\twhere customfield = 11708\n" +
            "),\n" +
            "--user.EntryTime 13222\n" +
            "creatTime as (\n" +
            "\tSELECT\n" +
            "\t\tissue,\n" +
            "\t\tdatevalue\n" +
            "\tfrom customfieldvalue\n" +
            "\twhere customfield = 13222\n" +
            "),\n" +
            "UserInfoAll as (\n" +
            "\tSELECT\n" +
            "\t    updated,\n" +
            "\t\t\n" +
            "\t\tCode.stringvalue as \"code\",\n" +
            "\t\tUserName.stringvalue as \"username\",\n" +
            "\t\tLoginName.display_name as \"loginname\",\n" +
            "\t\tLoginName.user_name as \"username2\",\n" +
            "\t\tregion.stringvalue as \"region\",\n" +
            "\t\tMainDept.stringvalue as \"domain\",\n" +
            "\t\tDept.stringvalue as \"department\",\n" +
            "\t\tStatus.customvalue as \"status\",\n" +
            "\t\tEmail.stringvalue as \"email\",\n" +
            "\t\tcreatTime.datevalue as \"creatTime\"\n" +
            "\t\t\n" +
            "\tfrom jiraissue\n" +
            "\tLEFT JOIN Code on Code.issue = jiraissue.id\n" +
            "\tLEFT JOIN UserName on UserName.issue = jiraissue.id\n" +
            "\tLEFT JOIN LoginName on LoginName.issue = jiraissue.id\n" +
            "\tLEFT JOIN Dept on Dept.issue = jiraissue.id\n" +
            "\tLEFT JOIN Status on Status.issue = jiraissue.id\n" +
            "\tLEFT JOIN Email on Email.issue = jiraissue.id\n" +
            "\tLEFT JOIN MainDept on MainDept.issue = jiraissue.id\n" +
            "\tLEFT JOIN region on region.issue = jiraissue.id\n" +
            "\tLEFT JOIN creatTime on creatTime.issue = jiraissue.id\n" +
            "\twhere project = 11600 or project = 12502\n" +
            "\t\t\n" +
            ")\n" +
            "SELECT * from UserInfoAll;";



    // 每天凌晨1点执行
    // @Scheduled(cron = "0 0/3 * * * ?")
    @Scheduled(cron = "0 0 1 * * ?")
    public void syncUser() {
        System.out.println("==========人员信息同步开始=========");
        Map<String, String> dept = new HashMap<>();
        dept.put("XA", "西安");
        dept.put("BJ", "北京");
        List<ProjectUserDO> addUser = new ArrayList<>();
        //获取JIRA库人员信息
        List<HashMap<String, Object>> userInfo = PGSQLUtil.loadUserData(sql);
        for (HashMap<String, Object> map : userInfo) {
            String jobNo = map.get("code").toString();
            /*if("XA0027".equals(jobNo)){
                System.out.println("dddddddddddd");
            }*/
            if(isInteger(jobNo)){
                //cnce用户
                ProjectUserDO user = userMapper.getUserByJobNo(Integer.parseInt(jobNo));
                // 无此员工，准备添加
                if (user == null) {
                    if (!"离职员工".equals(map.get("status"))) {
                        ProjectUserDO syncUser = getUserDo(map, jobNo);
                        addUser.add(syncUser);
                    }
                    //用户存在，检查是否需要更新信息
                }else {
                    ProjectUserDO syncUser = new ProjectUserDO();
                    syncUser.setCreateTime(new Date());
                    syncUser.setJobNo(user.getJobNo());
                    Integer jiraStatus = getUserStatus(map.get("status").toString());
                    syncUser.setUserStatus(jiraStatus);
                    if(map.containsKey("username") && map.get("username") != null){
                        syncUser.setUserName(map.get("username").toString());
                    }
                    if(map.containsKey("username2") && map.get("username2") != null){
                        syncUser.setAccount(map.get("username2").toString());
                    }
                    if(map.containsKey("email") && map.get("email") != null){
                        syncUser.setEmail(map.get("email").toString());
                    }
                    if(map.containsKey("region") && map.get("region") != null){
                        syncUser.setRegion(dept.get(map.get("region").toString()));
                    }
                    if(map.containsKey("department") && map.get("department") != null){
                        syncUser.setDepartment(map.get("department").toString());
                    }
                    if(map.containsKey("domain") && map.get("domain") != null){
                        syncUser.setDomain(map.get("domain").toString());
                    }
                        userMapper.update(syncUser);
                }
            }else {
                System.out.println("用户" + map + "工号格式错误");
            }
        }
        // 检查需要新增的User
        if(addUser.size() > 0){
            userMapper.saveUsers(addUser);
        }
    }

    private ProjectUserDO getUserDo(HashMap<String, Object> map, String jobNo){
        Map<String, String> dept = new HashMap<>();
        dept.put("XA", "西安");
        dept.put("BJ", "北京");
        ProjectUserDO syncUser = new ProjectUserDO();
        syncUser.setCreateTime(new Date());
        syncUser.setJobNo(Integer.parseInt(jobNo));
        Integer jiraStatus = getUserStatus(map.get("status").toString());
        syncUser.setUserStatus(jiraStatus);
        if(map.containsKey("username") && map.get("username") != null){
            syncUser.setUserName(map.get("username").toString());
        }
        if(map.containsKey("username2") && map.get("username2") != null){
            syncUser.setAccount(map.get("username2").toString());
        }
        if(map.containsKey("email") && map.get("email") != null){
            syncUser.setEmail(map.get("email").toString());
        }
        if(map.containsKey("region") && map.get("region") != null){
            syncUser.setRegion(dept.get(map.get("region")));
        }
        if(map.containsKey("department") && map.get("department") != null){
            syncUser.setDepartment(map.get("department").toString());
        }
        if(map.containsKey("domain") && map.get("domain") != null){
            syncUser.setDomain(map.get("domain").toString());
        }
        return syncUser;
    }

    private Integer getUserStatus(String status){
        switch (status){
            case "正式员工":
                return 0;
            case "离职员工":
                return 1;
            case "试用员工":
                return 2;
            case "未知员工":
                return 3;
            case "实习员工":
                return 4;
            default:
                break;
        }
        return 0;
    }

    private boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}