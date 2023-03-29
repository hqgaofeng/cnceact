package com.cnce.system.domain;

import java.io.Serializable;
import java.util.Date;

public class RequirementDO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer type;

    private Integer status;

    private String describe;

    private String sponsor;

    private String remark;

    private Date expectTime;

    private Date createTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(Date expectTime) {
        this.expectTime = expectTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "RequirementDO{" +
                "id=" + id +
                ", type=" + type +
                ", status=" + status +
                ", describe='" + describe + '\'' +
                ", sponsor='" + sponsor + '\'' +
                ", remark='" + remark + '\'' +
                ", expectTime=" + expectTime +
                ", createTime=" + createTime +
                '}';
    }
}
