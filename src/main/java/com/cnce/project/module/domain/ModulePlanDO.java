package com.cnce.project.module.domain;
import java.util.Date;

public class ModulePlanDO {
    //id
    private Integer mpId;
    //模块id
    private Integer mId;
    // 模块名称
    private String modName;
    // 计划名称
    private String planName;
    //计划开始时间
    private Date planStartTime;
    //计划结束时间
    private Date planEndTime;
    //需求总人力
    private String modPower;
    // 模块日历表
    private String modCalendar;
    //创建时间
    private Date createTime;

    public Integer getMpId() {
        return mpId;
    }

    public void setMpId(Integer mpId) {
        this.mpId = mpId;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getModName() {
        return modName;
    }

    public void setModName(String modName) {
        this.modName = modName;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Date getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    public Date getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }

    public String getModPower() {
        return modPower;
    }

    public void setModPower(String modPower) {
        this.modPower = modPower;
    }

    public String getModCalendar() {
        return modCalendar;
    }

    public void setModCalendar(String modCalendar) {
        this.modCalendar = modCalendar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

