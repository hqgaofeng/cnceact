package com.cnce.project.template.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnce.project.manpower.dao.EstimateDao;
import com.cnce.project.manpower.dao.ProjectUserDao;
import com.cnce.project.manpower.domain.EstimateDO;
import com.cnce.project.manpower.domain.ProjectUserDO;
import com.cnce.project.template.dao.TempSyncDao;
import com.cnce.project.template.dao.TemplateDao;
import com.cnce.project.template.domain.TempSyncDO;
import com.cnce.project.template.domain.TemplateDO;
import com.cnce.project.template.service.TemplateService;
import net.sf.ehcache.search.aggregator.Sum;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateDao tempMapper;
    @Autowired
    private TempSyncDao tempSyncMapper;
    @Autowired
    private EstimateDao estimateMapper;
    @Autowired
    private ProjectUserDao userMapper;

    @Override
    public TemplateDO get(int id) {
        TemplateDO temp =  tempMapper.get(id);
        // 通过jira_id查询项目名称
        TempSyncDO tmp = tempSyncMapper.getProjectByJId(temp.getJiraId());
        temp.setJiraId(tmp.getJiraId());
        temp.setpName(tmp.getpName());
        if("XA".equals(tmp.getRegion())){
            temp.setRegion("西安");
        }else {
            temp.setRegion("北京");
        }
        return temp;
    }

    @Override
    public List<TemplateDO> list(Map<String, Object> map) {
        if ("N".equals(map.get("region"))) {
            map.put("region", null);
        }
        if ("".equals(map.get("effectMonth")) || "请选择月份".equals(map.get("effectMonth"))) {
            map.put("effectMonth", null);
        }
        List<TemplateDO> list = tempMapper.list(map);
        return list;
    }

    @Override
    public int count(Map<String, Object> map) {
        return tempMapper.count(map);
    }

    @Override
    public int save(TemplateDO template) {
        String effectMonth = template.getEffectMonth();
        String year = effectMonth.substring(0, effectMonth.indexOf("-"));
        String month = effectMonth.substring(effectMonth.indexOf("-"), effectMonth.lastIndexOf("-"));
        String newDate = year + month;

        template.setEffectMonth(newDate);
        template.setCreateTime(new Date());
        // 通过项目名称查询项目信息
        TempSyncDO tmpSync = tempSyncMapper.getProjectByName(template.getpName(), template.getRegion());


        template.setJiraId(tmpSync.getJiraId());
        // 检查项目是否已经添加
        TemplateDO tmp = tempMapper.getTemplate(template);
        if(tmp == null){
            // 保存
            return tempMapper.save(template);
        }else {
            return 2;
        }
    }

    @Override
    public int update(TemplateDO template) {
        String effectMonth = template.getEffectMonth();
        if (effectMonth.contains("-")) {
            int sign = effectMonth.split("-").length - 1;
            if (sign > 1) {
                String year = effectMonth.substring(0, effectMonth.indexOf("-"));
                String month = effectMonth.substring(effectMonth.indexOf("-"), effectMonth.lastIndexOf("-"));
                String newDate = year + month;
                template.setEffectMonth(newDate);
            } else {
                template.setEffectMonth(effectMonth);
            }
        }
        template.setCreateTime(new Date());
        return tempMapper.update(template);
    }

    @Override
    public boolean isRev(int id) {
        Map<String, Object> map = new HashMap<>();
        // 通过项目id查到项目
        TemplateDO td = tempMapper.get(id);
        Integer jiraId = td.getJiraId();
//        String name = tempMapper.getProjectByName(jiraId);

        // 判断项目是否填写了人力数据
        if (td.getEffectMonth() != null) {
            map.put("effectMonth", td.getEffectMonth());
            // 通过月份查询project_estimate表记录
            List<EstimateDO> list = estimateMapper.list(map);

            if (list.size() > 0) {
                List<String> powList = new ArrayList<>();
                // 检查pro_manpower中是否有项目
                for (EstimateDO ed : list) {
                    int i = 1;
                    if (ed.getProManPower().contains(jiraId.toString())) {
                        powList.add(ed.getProManPower());
                    }
                }


                if (powList.size() > 0) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            // 没有分配项目，也就没有人力数据
            return true;
        }
    }

    @Override
    public int remove(int id) {
        int remove = tempMapper.remove(id);
        return remove;
    }

    private List<String> removeDuplicate(List<String> list) {
        HashSet h = new HashSet<>(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    @Override
    public int batchRemove(int[] ids) {
        return tempMapper.batchRemove(ids);
    }

    @Override
    public int removal(int id) {
        Map<String, Object> map = new HashMap<>();
        TemplateDO temp = tempMapper.get(id);
        map.put("effectMonth", temp.getEffectMonth());
        // 查出该月份的人力记录
        List<EstimateDO> list = estimateMapper.list(map);
        List<Integer> result = new ArrayList<>();
        int jirId = temp.getJiraId();
        // 遍历清除每条记录的人力数据
        if(list.size() > 0){
            for(EstimateDO ed : list){
                String manPower = ed.getProManPower();
                JSONObject mp = JSONObject.parseObject(manPower);
                // 转换man power json
                JSONObject jsonObj = conProManPower(jirId, manPower);
                // 比较旧的JSON对象
                Set<JSONObject> obj = compareJsonObj(jsonObj, mp);
                // 有要找的项目
                if(obj.size() > 0){
                    if(jsonObj.size() == 0){
                        // 该条用户在当月没有人力数据，可以直接删除该条记录
                        int r = estimateMapper.remove(ed.getId());
                        result.add(r);
                    }else {
                        BigDecimal value = sumObjectVal(obj);
                        EstimateDO estimate = new EstimateDO();
                        estimate.setId(ed.getId());
                        estimate.setUserId(ed.getUserId());
                        estimate.setEffectMonth(ed.getEffectMonth());
                        // 总计减去该人力值，未投入加上该人力值
                        BigDecimal totalManPower = new BigDecimal(ed.getTotalManpower());
                        BigDecimal nonManPower = new BigDecimal(ed.getNonInput());
                        BigDecimal total = totalManPower.subtract(value);
                        BigDecimal noInput = nonManPower.add(value);
                        estimate.setTotalManpower(total.toString());
                        estimate.setNonInput(noInput.toString());
                        estimate.setCreateTime(new Date());
                        estimate.setProManPower(jsonObj.toJSONString());
                        // 更新该条记录
                        int r = estimateMapper.update(estimate);
                        result.add(r);
                    }
                }


            }
        }
        if(result.size() > 0){
            return result.size();
        }else {
            return 0;
        }
    }

    // 转换man power
    private JSONObject conProManPower(int jirId, String json){
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonb = JSONObject.parseObject(json);
        for(Map.Entry<String, Object> enter : jsonb.entrySet()){
            String key = enter.getKey();
            // 如果项目不存在，则组装成新的json
            if(Integer.parseInt(key) != jirId){
                String value = enter.getValue().toString();
                jsonObj.put(key, value);
            }
        }
        return jsonObj;
    }

    // 比较两个JSON对象，找出不同的键值
    private Set<JSONObject> compareJsonObj(JSONObject a, JSONObject b){
        Set<JSONObject> set1 = new HashSet<>();
        Set<JSONObject> set2 = new HashSet<>();
        for(Map.Entry<String, Object> enter : a.entrySet()){
            JSONObject obj = new JSONObject();
            String key = enter.getKey();
            String value = enter.getValue().toString();
            obj.put(key, value);
            set1.add(obj);
        }

        for(Map.Entry<String, Object> et : b.entrySet()){
            JSONObject ob = new JSONObject();
            String key = et.getKey();
            String val = et.getValue().toString();
            ob.put(key, val);
            if(set1.add(ob)){
                set2.add(ob);
            }
        }
        return set2;
    }

//     JSONObject value求和
    private BigDecimal sumObjectVal(Set<JSONObject> set) {
        List<BigDecimal> list = new ArrayList<>();
        for (JSONObject ob : set) {
            for (Map.Entry<String, Object> enter : ob.entrySet()) {
                String val = enter.getValue().toString();
                if (StringUtils.isNotBlank(val) && NumberUtils.isCreatable(val)) {
                    BigDecimal value = new BigDecimal(val);
                    list.add(value);
                }
            }
        }
        BigDecimal sum = list.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum;
    }


    @Override
    public List<String> loadMonth() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        String currDate = String.valueOf(year) + "-" + String.valueOf(month);
        List<String> months = tempMapper.loadMonth();
        for (int i = 0; i < months.size(); i++) {
            if (currDate.equals(months.get(i))) {
                Collections.swap(months, i, 0);
            }
        }
        return months;
    }

    @Override
    public List<String> loadProject(String region) {
        return tempMapper.loadProject(region);
    }


    @Override
    public String getMsg(int id) {
        Map<String, Object> map = new HashMap<>();
        // 通过项目id查到项目
        TemplateDO td = tempMapper.get(id);
        Integer jiraId = td.getJiraId();
        String name = "";
        List<EstimateDO> powList = new ArrayList<>();
        // 判断项目是否填写了人力数据
        if (td.getEffectMonth() != null) {
            String month = td.getEffectMonth();
            map.put("effectMonth", month);
            // 通过月份查询project_estimate表记录
            List<EstimateDO> list = estimateMapper.list(map);
            if (list.size() > 0) {
                // 检查pro_manpower中是否有项目
                for (EstimateDO ed : list) {
                    if (ed.getProManPower().contains(jiraId.toString())) {
                        powList.add(ed);
                    }
                }
            }
            // 通过userId查询到部门id
            List<EstimateDO> poList = estimateMapper.queryList(powList);
            List<String> deptList = new ArrayList<>();
            for (EstimateDO ea : poList) {
                // 通过uid查到dept_id
                ProjectUserDO projectUser = userMapper.get(ea.getUserId().toString());
                deptList.add(projectUser.getDepartment() + "_" + projectUser.getDomain());
            }
            HashMap<String, Object> msgMap = new HashMap<>();
            msgMap.put("月份", month);
            List<String> lis = removeDuplicate(deptList);
            String strDept = StringUtils.join(lis, ",");
            String msg = "操作失败，项目绑定信息如下：月份{" + month + "}, 部门{" + strDept + "}";
            return msg;
        }
        return null;
    }
}