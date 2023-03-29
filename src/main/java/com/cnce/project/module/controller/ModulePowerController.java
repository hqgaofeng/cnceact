package com.cnce.project.module.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cnce.common.annotation.Log;
import com.cnce.common.utils.JSONUtils;
import com.cnce.common.utils.PageUtils;
import com.cnce.common.utils.Query;
import com.cnce.common.utils.R;
import com.cnce.project.business.service.BaselineService;
import com.cnce.project.module.dao.ModulePowerDao;
import com.cnce.project.module.domain.ModulePowerChangDO;
import com.cnce.project.module.domain.ModulePowerDO;
import com.cnce.project.module.domain.ModulePowerNewDO;
import com.cnce.project.module.domain.ModulePowerSaveDO;
import com.cnce.project.module.service.ModulePowerService;
import com.cnce.project.project.service.ProjectService;
import com.cnce.project.staff.domain.StaffDO;
import com.cnce.project.staff.service.StaffService;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jsoup.select.Evaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RequestMapping("/module/manpower")
@Controller
public class ModulePowerController {

    private final String prefix = "project/modpower";

    @Autowired
    private ModulePowerService modPowerService;
    @Autowired
    private BaselineService baseService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private StaffService staffService;

    @Autowired
    private ModulePowerDao modulePowerDao;

    @RequestMapping("/saveStaffName")
    @ResponseBody()
    @RequiresPermissions("module:manpower:edit")
    public R updateName(@RequestBody ModulePowerSaveDO modulePowerChangDO) {


        //解析JSON为map集合存入list
        String modPowerByMeId = modulePowerDao.getModPowerByMeId(modulePowerChangDO.getMeid());
        String headMP = modPowerByMeId.substring(0,6);
        String endMP = modPowerByMeId.substring(5,modPowerByMeId.length());
        String [] s = StringUtils.substringsBetween(endMP, "{", "}");
        List<Map<String,Object>> list = new ArrayList<>();
        for (int j = 0; j < s.length; j++) {
            s[j] = "{" + s[j] + "}";
            Map<String, Object> map = JSONUtils.jsonToMap(s[j]);
            list.add(map);
        }
        //判断操作 修改map
        StringBuilder newModPower = new StringBuilder();
        String operation = modulePowerChangDO.getOperation();
        if (operation.equals("proName")) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                if (map.get("staff_name").equals(modulePowerChangDO.getUserName()) &&
                        map.get("pro_name").equals(modulePowerChangDO.getProName()) &&
                        map.get("remark").equals(modulePowerChangDO.getRemark())){
                    map.put("pro_name",modulePowerChangDO.getNewValue());
                }
            }
            //拼接回string
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                JSONObject jsonObject = new JSONObject(map);
                String jsonString = JSON.toJSONString(jsonObject);
                newModPower.append(jsonString);
            }
            headMP = headMP + "[" + newModPower + "]}";
        }else if (operation.equals("staffName")){
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                if (map.get("staff_name").equals(modulePowerChangDO.getUserName()) &&
                        map.get("pro_name").equals(modulePowerChangDO.getProName()) &&
                            map.get("remark").equals(modulePowerChangDO.getRemark())){
                    map.put("staff_name",modulePowerChangDO.getNewValue());
                }
            }
            //拼接回string
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                JSONObject jsonObject = new JSONObject(map);
                String jsonString = JSON.toJSONString(jsonObject);
                newModPower.append(jsonString);
            }
            headMP = headMP + "[" + newModPower + "]}";
        }else if (operation.equals("changeManpower")) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                if (map.get("staff_name").equals(modulePowerChangDO.getUserName()) &&
                        map.get("pro_name").equals(modulePowerChangDO.getProName()) &&
                        map.get("remark").equals(modulePowerChangDO.getRemark())){
                    map.put("change_manpower",modulePowerChangDO.getNewValue());
                    map.put("non_input",modulePowerChangDO.getUncommitted());
                    map.put("actual_manpower",modulePowerChangDO.getActualManpower());
                    map.put("total_manpower",modulePowerChangDO.getTotal());
                }
            }
            //拼接回string
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                JSONObject jsonObject = new JSONObject(map);
                String jsonString = JSON.toJSONString(jsonObject);
                newModPower.append(jsonString);
            }
            headMP = headMP + "[" + newModPower + "]}";
        }else if (operation.equals("planManpower")) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                if (map.get("staff_name").equals(modulePowerChangDO.getUserName()) &&
                        map.get("pro_name").equals(modulePowerChangDO.getProName()) &&
                        map.get("remark").equals(modulePowerChangDO.getRemark())){
                    map.put("plan_manpower",modulePowerChangDO.getNewValue());
                    map.put("non_input",modulePowerChangDO.getUncommitted());
                    map.put("actual_manpower",modulePowerChangDO.getActualManpower());
                    map.put("total_manpower",modulePowerChangDO.getTotal());
                }
            }
            //拼接回string
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                JSONObject jsonObject = new JSONObject(map);
                String jsonString = JSON.toJSONString(jsonObject);
                newModPower.append(jsonString);
            }
            headMP = headMP + "[" + newModPower + "]}";
        }else if (operation.equals("remark")) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                if (map.get("staff_name").equals(modulePowerChangDO.getUserName()) &&
                        map.get("pro_name").equals(modulePowerChangDO.getProName()) &&
                        map.get("remark").equals(modulePowerChangDO.getRemark())){
                    map.put("remark",modulePowerChangDO.getNewValue());
                }
            }
            //拼接回string
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                JSONObject jsonObject = new JSONObject(map);
                String jsonString = JSON.toJSONString(jsonObject);
                newModPower.append(jsonString);
            }
            headMP = headMP + "[" + newModPower + "]}";
        }else {
            return R.error("后台未匹配到此操作！");
        }
        //保存到数据库
        modulePowerChangDO.setModPower(headMP);
        int i = modulePowerDao.updateModPower(modulePowerChangDO);

        //判断数据库操作成功与否
        if (i > 0){
            return R.ok();
        }
        return R.error("数据库保存时出错！");
    }

    @RequiresPermissions("module:manpower:add")
    @GetMapping("/add")
    public String add(Model model){
        return prefix + "/add";
    }

    @RequiresPermissions("module:manpower:manpower")
    @GetMapping()
    public String template(Model model) {
        return prefix + "/manpower";
    }


    @GetMapping("/list")
    @ResponseBody()
    public PageUtils list(@RequestParam Map<String, Object> params){
        Query query = new Query(params);
        List<ModulePowerNewDO> plan = modPowerService.list(query);
        int count = 0;
        List<ModulePowerDO> newPlan = new ArrayList<>();
        for (int i = 0; i < plan.size(); i++){
            Integer meId = plan.get(i).getMeId();
            String modName = plan.get(i).getModName();
            String modPower = plan.get(i).getModPower();
            //此处可能有异常，当数据库值为空时，或长度不足 应添加异常处理
            modPower = modPower.substring(5,modPower.length());
            //同理 此处也可能报异常 应添加异常处理
            String [] s = StringUtils.substringsBetween(modPower, "{", "}");
            for (int j = 0; j < s.length; j++) {
                s[j] = "{" + s[j] + "}";
                Map<String, Object> map = JSONUtils.jsonToMap(s[j]);
                ModulePowerDO mpdo = new ModulePowerDO();
                mpdo.setStaffName((String) map.get("staff_name"));
                mpdo.setTotalManpower((String) map.get("total_manpower"));
                mpdo.setPlanManpower((String) map.get("plan_manpower"));
                mpdo.setNonInput((String) map.get("non_input"));
                mpdo.setRemark((String) map.get("remark"));
                mpdo.setChangeManpower((String) map.get("change_manpower"));
                mpdo.setActualManpower((String) map.get("actual_manpower"));
                mpdo.setProName((String) map.get("pro_name"));
                mpdo.setModName(modName);
                mpdo.setMeId(meId);
                newPlan.add(mpdo);
                count++;
            }
        }
        for (int i = 0; i < newPlan.size(); i++) {
            System.out.println("******************");
            System.out.println(newPlan.get(i));
            System.out.println("******************");
        }
        int total = count;
        PageUtils pageUtil = new PageUtils(newPlan, total);
        return pageUtil;
    }

    @Log("保存模块人力数据")
    @RequestMapping("/save")
    @ResponseBody()
    @RequiresPermissions("module:manpower:add")
    public R save(ModulePowerDO modulePower) {
        if (modPowerService.save(modulePower) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("module:manpower:edit")
    @Log("保存模块人力数据")
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        ModulePowerDO powerDO = modPowerService.get(id);
        model.addAttribute("temp", powerDO);
        return prefix+"/edit";
    }

    @Log("更新模块人力数据")
    @RequestMapping("/update")
    @ResponseBody()
    @RequiresPermissions("module:manpower:edit")
    public R update(ModulePowerDO plan) {
        if (modPowerService.update(plan) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("module:manpower:remove")
    @Log("删除人力数据")
    @PostMapping("/remove")
    @ResponseBody
    public R remove(int id) {
        if(modPowerService.remove(id) > 0){
            return R.ok();
        }
        return R.error();

    }

    @RequiresPermissions("module:manpower:batchRemove")
    @Log("批量删除")
    @PostMapping("/batchRemove")
    @ResponseBody
    public R batchRemove(@RequestParam("ids[]") int[] bids) {
        int r = modPowerService.batchRemove(bids);
        if (r > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("module:manpower:staffInfo")
    @Log("人员信息")
    @GetMapping("/staffInfo/{id}")
    public String staffInfo(Model model, @PathVariable("id") int id) {
        ModulePowerDO power = modPowerService.get(id);
        // 查询人员信息
        StaffDO staff = staffService.getUserByName(power.getStaffName());
        model.addAttribute("temp", staff);
        return prefix+"/staffInfo";
    }

    @RequiresPermissions("module:manpower:projectInfo")
    @GetMapping("/projectInfo")
    public String staffInfo(Model model) {
        System.out.println("我要跳转了！====================================================");
        return prefix+"/projectInfo";
    }

    @GetMapping("/loadModule")
    @ResponseBody()
    public List<String> loadModule(){
        List<String> modList = baseService.loadModules();
        return modList;
    }

    @GetMapping("/loadProject")
    @ResponseBody()
    public List<String> loadProject(){
        List<String> proList = projectService.loadProject();
        return proList;
    }

    @GetMapping("/loadStaff")
    @ResponseBody()
    public List<String> loadStaff(){
        List<String> proStaff = staffService.loadStaff();
        return proStaff;
    }

    @ResponseBody()
    @GetMapping("/name")
    public List<String> getAllName() {
        System.out.println("66666666666666:::6666666666");
        return modPowerService.findAllName();
    }

    @ResponseBody()
    @GetMapping("/pname")
    public List<String> getAllpName() {
        return modPowerService.findAllpName();
    }


    @ResponseBody()
    @GetMapping("/selectPName")
    public List<String> getAllMod_name() {
        return modPowerService.findAllMod_name();
    }




}
