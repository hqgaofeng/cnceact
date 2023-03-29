package com.cnce.project.manpower.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnce.common.utils.JSONUtils;
import com.cnce.common.utils.StringUtils;
import com.cnce.project.manpower.dao.EstimateDao;
import com.cnce.project.manpower.dao.PowerDao;
import com.cnce.project.manpower.dao.ProjectUserDao;
import com.cnce.project.manpower.domain.EstimateDO;
import com.cnce.project.manpower.domain.PowerDO;
import com.cnce.project.manpower.domain.ProjectUserDO;
import com.cnce.project.manpower.service.PowerService;
import com.cnce.project.template.dao.TemplateDao;
import com.cnce.project.template.domain.TemplateDO;
import com.cnce.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PowerServiceImpl implements PowerService {

    @Autowired
    private EstimateDao mateMapper;
    @Autowired
    private TemplateDao tempMapper;
    @Autowired
    private ProjectUserDao userMapper;
    @Autowired
    private PowerDao powerMapper;

    @Override
    public int savePowers(Map<String, Object> map, UserDO userDO) {
        Map<String, Object> tmpMap = new HashMap<>();
        // 先检查同月份同用户是否存在记录，存在则更新json，不存在则新增记录
        String effectMonth = map.get("effectMonth").toString();
        Integer userId = Integer.valueOf(map.get("userId").toString());
        EstimateDO pd = mateMapper.getEstimateByMonth(effectMonth, userId);


        String key = map.get("name").toString();
        String value = map.get("value").toString();
        String region = map.get("region").toString();

        tmpMap.put("effectMonth", effectMonth);
        tmpMap.put("pName", key);
        tmpMap.put("region", region);


        EstimateDO ed = new EstimateDO();
        ed.setUserId(userId);
        ed.setTotalManpower(map.get("total").toString());
        ed.setNonInput(map.get("noinput").toString());
        ed.setEffectMonth(effectMonth);
        ed.setCreateTime(new Date());

        // 通过月份和项目名称查询jira id
        List<TemplateDO> temp = tempMapper.list(tmpMap);

        String k = temp.get(0).getJiraId().toString();

        if (pd == null) {
            // 新增
            JSONObject jsonPower = new JSONObject();
            jsonPower.put(k, value);
            ed.setProManPower(jsonPower.toString());
            return mateMapper.save(ed);
        }
        // 更新


        return updatePowers(k, value, pd, ed);
    }



    private int updatePowers(final String k, final String value, EstimateDO pd, final EstimateDO ed) {
        String proPower = pd.getProManPower();
        JSONObject jsonPower = JSONObject.parseObject(proPower);
        Iterator iterator = jsonPower.entrySet().iterator();
        // 新的JSON对象
        JSONObject newJson = JSONObject.parseObject(jsonPower.toJSONString());
        boolean found = false;
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = entry.getKey().toString();
            if (key.equals(k)) {
                String v = entry.getValue().toString();
                found = true;
                if (!v.equals(value)) {
                    // 更新json
                    newJson.put(k, value);
                }
                break;
            }
        }
        if (!found) {
            // 新增
            newJson.put(k, value);
        }
        System.out.println("newJson::"+newJson);
        // 更新对象
        ed.setProManPower(newJson.toString());
        System.out.println("ed.getProManPower()::"+ed.getProManPower());

        return mateMapper.updEstimate(ed);
    }

    private List<ProjectUserDO> findUsers(Map<String, Object> map) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.putAll(map);
        if("XA".equals(map.get("region"))){
            userMap.put("region", "西安");
        }else {
            userMap.put("region", "北京");
        }
        // 查询人员列表
        List<ProjectUserDO> userList = userMapper.list(userMap);
        return userList;
    }

    @Override
    public List<PowerDO> list(Map<String, Object> map) {
        List<PowerDO> list = powerMapper.list(map);
        return list;
    }

    @Override
    public int count(Map<String, Object> map) {
        return powerMapper.count(map);
    }

    @Override
    public int remove(int id) {
        int result = powerMapper.remove(id);
        return result;
    }

    @Override
    public List<Map<String, Object>> getList(Map<String, Object> map) {
        final List<ProjectUserDO> users = findUsers(map);
        final String effectMonth = map.get("effectMonth").toString();
        final String department = map.get("department").toString();
        final String domain = map.get("domain").toString();
        // 查出月份对应的所有项目信息
        final List<TemplateDO> pros = tempMapper.getProjectList(map);

        if(users.size() > 0){
            // 定义返回的List
            final List<Map<String, Object>> nPower = new ArrayList<>(users.size());
            for (ProjectUserDO u : users) {
                final Integer userId = u.getUserId();
                final String username = u.getUserName();
                if(!"".equals(department)){
                    if(!"".equals(domain)){
                        nPower.add(constructUserInfo(userId, username, domain, effectMonth, pros));
                    }else {
                        nPower.add(constructUserInfo(userId, username, department, effectMonth, pros));
                    }
                }
            }
            return nPower;
        }
        return null;
    }

    private Map<String, Object> constructUserInfo(final Integer userId, final String username, final String deptName,
                                                  final String effectMonth, final List<TemplateDO> pros) {
        // 通过用户ID和月份，查询用户的人力数据
        final EstimateDO pd = mateMapper.getEstimateByMonth(effectMonth, userId);
        LinkedHashMap<String, Object> userMap = new LinkedHashMap<>();
        // 装配用户信息
        /**
         * 1 姓名
         * 2 角色
         * 3 总计
         * 4 未投入
         */
        userMap.put("userId", userId.toString());
        userMap.put("1", username);
        userMap.put("2", deptName);
        if (pd == null) {
            // 添加对象
            userMap.put("3", null);
            userMap.put("4", null);
            for (TemplateDO td : pros) {
                final String pName = td.getpName();
                userMap.put(pName, null);
            }
            return userMap;
        }
        // 有数据，检查当前项目的人力数据，添加该项目
        userMap.put("3", pd.getTotalManpower());
        userMap.put("4", pd.getNonInput());
        for (TemplateDO td : pros) {
            final String pName = td.getpName();
            final String jiraId = String.valueOf(td.getJiraId());
            String proPower = pd.getProManPower();
            userMap.put(pName, JSONUtils.getJsonValue(proPower, jiraId));
        }
        return userMap;
    }

}