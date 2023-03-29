package com.cnce.manager.report.service.impl;

import com.cnce.manager.file.DownFile;
import com.cnce.manager.report.dao.ReportDao;
import com.cnce.manager.report.domain.ReportDO;
import com.cnce.manager.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportMapper;

    private DownFile downFile;

    public List<ReportDO> getReportInfo(Map<String,Object> map){
        if (map.get("startTime").equals("null")||map.get("startTime").equals("")){
            map.remove("startTime");
        }
        if (map.get("endTime").equals("null")|| map.get("endTime").equals("")){
            map.remove("endTime");
        }
        if (map.get("toolName").equals("null")){
            map.remove("toolName");
        }
        List<ReportDO> list=reportMapper.getReportInfo(map);
        for (int i=0;i<list.size();i++){
            if (list.get(i).getReportPath()==null || list.get(i).getReportPath()==""){
                list.remove(i);
            }
        }
        return list;
    }

    public int count(Map<String, Object> map){
        return reportMapper.count(map);
    }


    public String  getReportPath(Integer id){
        ReportDO path=reportMapper.getReportPath(id);
        String reportPath=path.getReportPath();
        return reportPath;
    }

    public void downFile(Integer id, HttpServletResponse response)  {
        String reportPath=getReportPath(id);
        String fileName = reportPath.split("/")[reportPath.split("/").length-1].trim();
        DownFile dof=new DownFile();
        dof.downfiles(response, reportPath,fileName);
    }
}
