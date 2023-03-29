package com.cnce.project.manpower.controller;

import com.cnce.project.manpower.domain.ProjectUserDO;
import com.cnce.project.manpower.service.ProjectUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@RequestMapping("/manpower/user")
@Controller
public class ProjectUserController {

    @Autowired
    private ProjectUserService userService;

    @GetMapping("/domain")
    @ResponseBody
    public List<ProjectUserDO> getDomain(@RequestParam("region")String region,
                                  @RequestParam("department")String department) {
        return userService.getDomain(region, department);
    }

    @GetMapping("/department")
    @ResponseBody
    public List<ProjectUserDO> loadDepartment(@RequestParam("region")String region) {
        return userService.getDepartment(region);
    }

}
