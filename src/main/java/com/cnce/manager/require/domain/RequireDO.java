package com.cnce.manager.require.domain;

import java.util.Date;

public class RequireDO {
    // id
    private Integer id;
    // 工具名称
    private String toolName;
    // 需求类型
    private Integer type;
    // 需求描述
    private String describe;
    // 发起人
    private String sponsor;
    // 部门意见
    private String deptOpinion;
    // 收益分析
    private String incomeAnalysis;
    // 是否立项
    private Integer isApprove;
    // 期望交付日期
    private Date expectDueTime;
    // 实际交付日期
    private Date actualDelTime;
    // 创建时间
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getDeptOpinion() {
        return deptOpinion;
    }

    public void setDeptOpinion(String deptOpinion) {
        this.deptOpinion = deptOpinion;
    }

    public String getIncomeAnalysis() {
        return incomeAnalysis;
    }

    public void setIncomeAnalysis(String incomeAnalysis) {
        this.incomeAnalysis = incomeAnalysis;
    }

    public Integer getIsApprove() {
        return isApprove;
    }

    public void setIsApprove(Integer isApprove) {
        this.isApprove = isApprove;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpectDueTime() {
        return expectDueTime;
    }

    public void setExpectDueTime(Date expectDueTime) {
        this.expectDueTime = expectDueTime;
    }

    public Date getActualDelTime() {
        return actualDelTime;
    }

    public void setActualDelTime(Date actualDelTime) {
        this.actualDelTime = actualDelTime;
    }

    @Override
    public String toString() {
        return "RequireDO{" +
                "id=" + id +
                ", toolName='" + toolName + '\'' +
                ", type=" + type +
                ", describe='" + describe + '\'' +
                ", sponsor='" + sponsor + '\'' +
                ", deptOpinion='" + deptOpinion + '\'' +
                ", incomeAnalysis='" + incomeAnalysis + '\'' +
                ", isApprove=" + isApprove +
                ", expectDueTime=" + expectDueTime +
                ", actualDelTime=" + actualDelTime +
                ", createTime=" + createTime +
                '}';
    }
}
