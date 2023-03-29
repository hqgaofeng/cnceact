package com.cnce.manager.tools.service.impl;

import com.cnce.common.constant.ToolsConstant;
import com.cnce.manager.file.DownFile;
import com.cnce.manager.tools.dao.ToolsDao;
import com.cnce.manager.tools.domain.ToolsDO;
import com.cnce.manager.tools.service.ToolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@Service
public class ToolsServiceImpl implements ToolsService {
	@Autowired
	private ToolsDao toolsMapper;

    private DownFile downFile;


    @Override
    public ToolsDO get(Integer id) {
        return toolsMapper.get(id);
    }

    @Override
    public Integer getDeptId(Integer id){
        Integer deptId =  toolsMapper.getToolsDeptIdById(id).getDeptId();
        return deptId;
    }

    public List<String> getToolsName(){
        List<ToolsDO> list = toolsMapper.getToolsName();
        List<String> resList=new ArrayList<>();
        for (int i=0;i< list.size();i++){
            String toolName=list.get(i).getName();
            resList.add(toolName);
        }
        return resList;
    }

    public void downFile(String  fileName, HttpServletResponse response){
        String filePath ="/cnce/tools/"+fileName;
        DownFile dof=new DownFile();
        dof.downfiles(response, filePath,fileName);
    }


    public Integer getToolIdByName(String toolName) {
        Integer toolId=toolsMapper.getToolIdByName(toolName);
        return toolId;
    }
}
