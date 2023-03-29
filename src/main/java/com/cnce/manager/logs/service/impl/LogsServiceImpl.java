package com.cnce.manager.logs.service.impl;

import com.cnce.manager.file.DownFile;
import com.cnce.manager.logs.dao.LogsDao;
import com.cnce.manager.logs.domain.LogsDO;
import com.cnce.manager.logs.service.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
@Service
public class LogsServiceImpl implements LogsService {

    @Autowired
    private LogsDao logsMapper;

    private DownFile downFile;

    public List<LogsDO> getlogsInfo(Map<String,Object> map){
        if (map.get("startTime").equals("null")||map.get("startTime").equals("")){
            map.remove("startTime");
        }
        if (map.get("endTime").equals("null")|| map.get("endTime").equals("")){
            map.remove("endTime");
        }
        if (map.get("toolName").equals("null")){
            map.remove("toolName");
        }
        List<LogsDO> list=logsMapper.getLogsInfo(map);
        for (int i=0;i<list.size();i++){
            if (list.get(i).getLogPath()==null || list.get(i).getLogPath()==""){
                list.remove(i);
            }
        }
        return list;
    }

    public int count(Map<String, Object> map){
        return logsMapper.count(map);
    }

    public String  getLogsPath(Integer id){
        LogsDO path=logsMapper.getLogsPath(id);
        String logPath=path.getLogPath();
        return logPath;
    }

    public void downFile(Integer id, HttpServletResponse response)  {
        String logPath=getLogsPath(id);
        String fileName = logPath.split("/")[logPath.split("/").length-1].trim();
        DownFile dof=new DownFile();
        dof.downfiles(response, logPath,fileName);
    }
}
