package com.cnce.common.service.impl;

import com.cnce.common.dao.RmopLogDao;
import com.cnce.common.domain.RmopLogDO;
import com.cnce.common.service.RmopLogService;
import com.cnce.common.utils.HttpContextUtils;
import com.cnce.common.utils.IPUtils;
import com.cnce.common.utils.ShiroUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RmopLogServiceImpl implements RmopLogService {

    private static Logger logger = LoggerFactory.getLogger(RmopLogServiceImpl.class);
    //发送数据缓冲区

    @Autowired
    private RmopLogDao rmopLogDao;

    @Override
    public RmopLogDO get(Long id) {
        //
        return rmopLogDao.get(id);
    }

    @Override
    public List<RmopLogDO> list(Map<String, Object> map) {
        //
        return rmopLogDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        //
        return rmopLogDao.count(map);
    }

    @Override
    public int save(String deviceNo,String method,String operation) {
        RmopLogDO log = new RmopLogDO();
        try {
            if(null==ShiroUtils.getUser()){
                log.setUserId(-1L);
                log.setUserName("用户信息为空!");
            }else{
                log.setUserId(ShiroUtils.getUserId());
                log.setUserName(ShiroUtils.getUser().getUsername());
            }
            log.setIp(IPUtils.getIpAddr(HttpContextUtils.getHttpServletRequest()));
            log.setDeviceNo(deviceNo);
            log.setMethod(method);
            log.setOperation(operation);
            log.setCreateTime(new Date());
            return rmopLogDao.save(log);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return 0;
    }

    @Override
    public int batchRemove(Long[] ids) {
        //
        return rmopLogDao.batchRemove(ids) ;
    }

}
