package com.cnce.project.manpower.controller;


import com.cnce.project.manpower.domain.ProjectDeptDO;
import com.cnce.project.manpower.service.ProjectDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@RequestMapping("/manpower/dept")
@Controller
public class ProjectDeptController {

    @Autowired
    private ProjectDeptService deptService;

    @GetMapping("/deptName")
    @ResponseBody
    public List<ProjectDeptDO> getDeptName() {
        return deptService.getDeptName();
    }

    @GetMapping("/deptment")
    @ResponseBody
    public List<ProjectDeptDO> loadDepartment(@RequestParam("deptId")String deptId) {
        return deptService.loadDepartment(Integer.parseInt(deptId));
    }






}
