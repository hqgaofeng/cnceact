package com.cnce.project.manpower.domain;

import com.cnce.project.template.domain.TemplateDO;

import java.util.*;

public class EstimateDO extends TemplateDO {
    // 主键id
    private Integer id;
    // 用户id
    private Integer userId;
    //人力总和
    private String totalManpower;
    // 未投入
    private String nonInput;
    // 用户关联的项目人力数据
    private String proManPower;
    //项目投入月份
    private String effectMonth;
    // 创建时间
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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

    public String getProManPower() {
        return proManPower;
    }

    public void setProManPower(String proManPower) {
        this.proManPower = proManPower;
    }

    @Override
    public String getEffectMonth() {
        return effectMonth;
    }

    @Override
    public void setEffectMonth(String effectMonth) {
        this.effectMonth = effectMonth;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "EstimateDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", totalManpower='" + totalManpower + '\'' +
                ", nonInput='" + nonInput + '\'' +
                ", proManPower='" + proManPower + '\'' +
                ", effectMonth='" + effectMonth + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
