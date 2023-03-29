package com.cnce.project.manpower.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cnce.common.utils.StringUtils;
import com.cnce.project.manpower.dao.ProjectLogDao;
import com.cnce.project.manpower.dao.ProjectUserDao;
import com.cnce.project.manpower.domain.ProjectLogDO;
import com.cnce.project.manpower.domain.ProjectUserDO;
import com.cnce.project.manpower.service.ProjectLogService;
import com.cnce.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProjectLogServiceImpl implements ProjectLogService {

    @Autowired
    private ProjectLogDao logMapper;
    @Autowired
    private ProjectUserDao userMapper;


    @Override
    public List<ProjectLogDO> getAllLogInfo(Map<String, Object> params) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        //增加判断是否为空，防止空指针异常报错
        String startTime = params.get("startTime") == null ? "" : params.get("startTime").toString();
        String endTime = params.get("endTime") == null ? "" : params.get("endTime").toString();

        if (StringUtils.isEmpty(startTime) || startTime.equals("起始时间")) {
            params.put("startTime", formatter.format(calendar.getTime()));
        }
        if (StringUtils.isEmpty(endTime) || endTime.equals("截止时间")) {
            params.put("endTime", formatter.format(date));
        }

        List<ProjectLogDO> list = logMapper.getAllLogInfo(params);
        return list;
    }


    @Override
    public int savePowerLog(Map<String, Object> map, UserDO userDO) {
        String userId = map.get("userId").toString();
        ProjectUserDO user = userMapper.get(userId);
        ProjectLogDO logDO = new ProjectLogDO();
        logDO.setUserId(Integer.parseInt(userDO.getUserId().toString()));
        logDO.setUserDept(userDO.getDeptName());
        logDO.setUserName(userDO.getName());
        // 修改明细
        JSONObject jsonb = new JSONObject();
        String commMsg = "姓名{" + user.getUserName() + "}";
        jsonb.put("人员信息", commMsg);
        jsonb.put("总计", "变化值{" + map.get("total") + "}");
        jsonb.put("未投入", "变化值{" + map.get("noinput") + "}");
        String proMsg = "修改的项目{" + map.get("name") + "}, 该项目人力{" + map.get("value") + "}";
        jsonb.put("项目信息", proMsg);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("修改时间", map.get("effectMonth"));
        jsonObj.put("明细" + map.get("userId"), jsonb);
        String editInfo = JSON.toJSONString(jsonObj, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        logDO.setEditInfo(editInfo);
        logDO.setCreateTime(new Date());
        int result = logMapper.saveLog(logDO);
        return result;
    }

    @Override
    public Integer count(Map<String, Object> params) {
        Integer num = logMapper.count(params);
        return num;
    }
}
