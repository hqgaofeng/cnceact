package com.cnce.project.project.domain;
import java.util.Date;

public class ProjectPlanDO {
    //id
    private Integer planId;
    //项目id
    private Integer pId;
    // 项目名称
    private String pName;
    // 计划名称
    private String planName;
    // 版本目的
    private String proPlan;
    //计划开始时间
    private Date planStartTime;
    //计划结束时间
    private Date planEndTime;
    //测试周期
    private int testCycle;
    //需求总人力
    private String totalPower;
    //填写人（接口人）
    private String written;
    //创建时间
    private Date createTime;

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getProPlan() {
        return proPlan;
    }

    public void setProPlan(String proPlan) {
        this.proPlan = proPlan;
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

    public int getTestCycle() {
        return testCycle;
    }

    public void setTestCycle(int testCycle) {
        this.testCycle = testCycle;
    }

    public String getTotalPower() {
        return totalPower;
    }

    public void setTotalPower(String totalPower) {
        this.totalPower = totalPower;
    }

    public String getWritten() {
        return written;
    }

    public void setWritten(String written) {
        this.written = written;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

