package com.cnce.manager.accpt.service.impl;

import com.cnce.manager.accpt.dao.AccptDao;
import com.cnce.manager.accpt.domain.AccptDO;
import com.cnce.manager.accpt.service.AccptService;
import com.cnce.manager.require.domain.RequireDO;
import com.cnce.manager.tools.service.impl.ToolsServiceImpl;
import com.cnce.system.dao.UserDao;
import com.cnce.system.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Service
public class AccptServiceImpl implements AccptService {

    @Autowired
    private AccptDao accptMapper;
    @Autowired
    private ToolsServiceImpl toolsService;

    @Autowired
    private UserServiceImpl userService;

    public List<AccptDO> getAccptInfo(Map<String,Object> map){

        List<AccptDO> list=accptMapper.getAccptInfo(map);
        return list;
    }

    public int count(Map<String, Object> map){
        return accptMapper.count(map);
    }
//    public int save(String toolName,String opinion,String accptResult,String accptTime,Long userId,String userName){
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        AccptDO accpt = new AccptDO();
//        accpt.setToolName(toolName);
//        accpt.setOpinion(opinion);
//        accpt.setAccptResult(Integer.parseInt(accptResult));
//        accpt.setToolId(toolsService.getToolIdByName(toolName));
//        accpt.setUserId(Math.toIntExact(userId));
//        accpt.setUserName(userName);
//        try{
//            Date date1=format.parse(accptTime);
//            accpt.setAccptTime(date1);
//        }catch(ParseException e){
//            e.printStackTrace();
//        }
//        int count=accptMapper.save(accpt);
//        return count;
//    }

    public int save(AccptDO accpt){
        Integer toolId=toolsService.getToolIdByName(accpt.getToolName());
        accpt.setToolId(toolId);
        int count=accptMapper.save(accpt);
        return count;
    }

}
