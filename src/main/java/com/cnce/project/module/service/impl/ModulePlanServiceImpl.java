package com.cnce.project.module.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnce.common.utils.DateUtils;
import com.cnce.common.utils.JSONUtils;
import com.cnce.project.business.dao.BaselineDao;
import com.cnce.project.business.domain.BaselineDO;
import com.cnce.project.module.dao.ModulePlanDao;
import com.cnce.project.module.domain.ModulePlanDO;
import com.cnce.project.module.service.ModulePlanService;
import com.cnce.project.project.dao.ProjectPlanDao;
import com.cnce.project.project.domain.ProjectPlanDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class ModulePlanServiceImpl implements ModulePlanService {

    @Autowired
    private ModulePlanDao modulePlanMapper;
    @Autowired
    private ProjectPlanDao proPlanMapper;
    @Autowired
    private BaselineDao baselineMapper;

    /**
     * 加载模块人力
     * @param map
     * @return
     */
    @Override
    public List<Map<String, Object>> loadData(Map<String, Object> map) {
        // 定义返回的List
        final List<Map<String, Object>> datas = new ArrayList<>();
        // 通过计划名称，查出版本计划信息
        String planName = map.get("planName").toString();
        ProjectPlanDO projectPlan = proPlanMapper.getProjectByPlan(planName);
        if(projectPlan != null){
            // 模块信息
            String modName = map.get("modName").toString();
            if(modName == null || "".equals(modName)){
                // 获取所有的模块
                List<BaselineDO> modules = baselineMapper.list(new HashMap<>());
                for(BaselineDO bd : modules){
                    Map<String, Object> mp = constructPlanInfo(bd.getbId(), bd.getModule(), projectPlan);
                    datas.add(mp);
                }
            }else {
                // 按当前模块组装数据
                List<BaselineDO> modList = new ArrayList<>();
                // 通过模块名称查到模块数据
                BaselineDO baseline = baselineMapper.getModuleByName(modName);
                modList.add(baseline);
                for(BaselineDO b : modList){
                    Map<String, Object> ma = constructPlanInfo(b.getbId(), b.getModule(), projectPlan);
                    datas.add(ma);
                }
            }
        }
        return datas;
    }

    private Map<String, Object> constructPlanInfo(final Integer mId, final String modName, ProjectPlanDO projectPlan) {
        LinkedHashMap<String, Object> modPlan = new LinkedHashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String planName = projectPlan.getPlanName();
        Date beginTime = projectPlan.getPlanStartTime();
        Date endTime = projectPlan.getPlanEndTime();
        // 计划开始时间
        modPlan.put("3", sdf.format(beginTime));
        // 计划结束时间
        modPlan.put("4", sdf.format(endTime));
        try {
            List<String> dateList = DateUtils.getDates(sdf.format(beginTime), sdf.format(endTime));
            // 通过模块ID和计划名称，查询模块计划
            final ModulePlanDO mPlan = modulePlanMapper.getPlanByModuleId(mId, planName);
            if(mPlan == null){
                // 没有记录，添加对象
                modPlan.put("mId", mId);
                // 模块名称
                modPlan.put("1", modName);
                // 模块人力
                modPlan.put("2", null);
                for(String da : dateList){
                    modPlan.put(da, null);
                }
                return modPlan;
            }
            // 有记录，解析对象
            modPlan.put("mId", mId);
            modPlan.put("1", modName);
            modPlan.put("2", mPlan.getModPower());
            for(String d : dateList){
                modPlan.put(d, JSONUtils.getJsonValue(mPlan.getModCalendar(), d));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return modPlan;
    }

    /**
     * 保存模块人力数据
     * @param map
     * @return
     */
    @Override
    public int saveDatas(Map<String, Object> map) {
        String mId = map.get("mId").toString();
        String planName = map.get("planName").toString();
        String modPower = map.get("modPower").toString();
        String key = map.get("name").toString();
        String value = map.get("value").toString();
        // 新保存的ModulePlanDO
        ModulePlanDO mp = new ModulePlanDO();
        mp.setmId(Integer.parseInt(mId));
        mp.setPlanName(planName);
        mp.setModPower(modPower);
        JSONObject calendar = new JSONObject();
        calendar.put(key, value);
        mp.setModCalendar(calendar.toString());
        mp.setCreateTime(new Date());
        // 通过计划名称和模块id查询是否有记录，有记录则更新，无记录则新增
        ModulePlanDO modPlan = modulePlanMapper.getPlanByModuleId(Integer.parseInt(mId), planName);
        if(modPlan == null){
            // 新增
            if(value != null && !"".equals(value)){
                return modulePlanMapper.save(mp);
            }else {
                return 1;
            }
        }
        // 更新
        return updatePowers(key, value, mp, modPlan);
    }

    /**
     * 比较模块人力并更新
     * @param key 要更新的名称
     * @param value 要更新的人力
     * @param newPlan 新模块人力对象
     * @param oldPlan 旧模块人力对象
     * @return
     */
    private int updatePowers(String key, String value, ModulePlanDO newPlan, final ModulePlanDO oldPlan) {
        String modCalendar = oldPlan.getModCalendar();
        JSONObject jsonObj = JSONObject.parseObject(modCalendar);
        Iterator iterator = jsonObj.entrySet().iterator();
        // 新的JSON对象
        JSONObject newJson = JSONObject.parseObject(jsonObj.toJSONString());
        boolean found = false;
        while(iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String k = entry.getKey().toString();
            if (key.equals(k)) {
                String v = entry.getValue().toString();
                found = true;
                if (!v.equals(value) && value != null && !"".equals(value)) {
                    // 更新json
                    newJson.put(k, value);
                }
                break;
            }
        }
        if (!found) {
            // 新增
            if(value != null && !"".equals(value)){
                newJson.put(key, value);
            }
        }
        // 更新对象
        newJson.remove(key);
        oldPlan.setModPower(newPlan.getModPower());
        oldPlan.setModCalendar(newJson.toString());
        oldPlan.setCreateTime(new Date());
        return modulePlanMapper.update(oldPlan);
    }
}