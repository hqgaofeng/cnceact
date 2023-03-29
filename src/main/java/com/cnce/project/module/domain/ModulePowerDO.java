package com.cnce.project.module.domain;
import lombok.ToString;

import java.util.Date;

@ToString
public class ModulePowerDO {
    //模块id
    private Integer meId;
    // 模块名称
    private String modName;
    // 人员姓名
    private String staffName;
    // 项目名称
    private String proName;
    // 安排日期
    private String planDate;
    // 计划人力
    private String planManpower;
    // 实际人力
    private String actualManpower;
    // 变动人力
    private String changeManpower;
    // 人力备注
    private String remark;
    //创建时间
    private Date createTime;

    private String totalManpower;

    private String nonInput;

    public String getModPower() {
        return modPower;
    }

    public void setModPower(String modPower) {
        this.modPower = modPower;
    }

    private String modPower;

    public String getTotalManpower() {
        return totalManpower;
    }

    public void setTotalManpower(String totalManpower) {
        this.totalManpower = totalManpower;
    }

    public String getNonInput() {
        return nonInput;
    }

    public void setNonInput(String nonInput) {
        this.nonInput = nonInput;
    }

    public Integer getMeId() {
        return meId;
    }

    public void setMeId(Integer meId) {
        this.meId = meId;
    }

    public String getModName() {
        return modName;
    }

    public void setModName(String modName) {
        this.modName = modName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getPlanManpower() {
        return planManpower;
    }

    public void setPlanManpower(String planManpower) {
        this.planManpower = planManpower;
    }

    public String getActualManpower() {
        return actualManpower;
    }

    public void setActualManpower(String actualManpower) {
        this.actualManpower = actualManpower;
    }

    public String getChangeManpower() {
        return changeManpower;
    }

    public void setChangeManpower(String changeManpower) {
        this.changeManpower = changeManpower;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}

