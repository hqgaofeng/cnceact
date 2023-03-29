package com.cnce.project.project.domain;
import java.util.Date;

public class ProjectSummaryDO {
    //id
    private Integer psId;
    // 项目名称
    private String proName;
    // 接口人
    private String written;
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
    //每日需求人力
    private String dailyReqPower;
    //总欠缺人力
    private String totalLackPower;
    //日欠缺人力
    private String dailyLackPower;
    // 今日计划人力
    private String planManpower;
    // 今日实际人力
    private String actualManpower;
    // 已计划总人力
    private String planTotalManpower;
    // 已投入人力
    private String inputTotalManpower;
    // 日期
    private String planDate;
    //创建时间
    private Date createTime;

    public Integer getPsId() {
        return psId;
    }

    public void setPsId(Integer psId) {
        this.psId = psId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getWritten() {
        return written;
    }

    public void setWritten(String written) {
        this.written = written;
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

    public String getDailyReqPower() {
        return dailyReqPower;
    }

    public void setDailyReqPower(String dailyReqPower) {
        this.dailyReqPower = dailyReqPower;
    }

    public String getTotalLackPower() {
        return totalLackPower;
    }

    public void setTotalLackPower(String totalLackPower) {
        this.totalLackPower = totalLackPower;
    }

    public String getDailyLackPower() {
        return dailyLackPower;
    }

    public void setDailyLackPower(String dailyLackPower) {
        this.dailyLackPower = dailyLackPower;
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

    public String getPlanTotalManpower() {
        return planTotalManpower;
    }

    public void setPlanTotalManpower(String planTotalManpower) {
        this.planTotalManpower = planTotalManpower;
    }

    public String getInputTotalManpower() {
        return inputTotalManpower;
    }

    public void setInputTotalManpower(String inputTotalManpower) {
        this.inputTotalManpower = inputTotalManpower;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

