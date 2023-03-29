package com.cnce.project.business.domain;
import java.util.Date;

public class BaselineDO {
    //id
    private Integer bId;
    //领域
    private String scope;
    //模块
    private String module;
    //基线
    private String baseline;
    //创建时间
    private Date createTime;

    public Integer getbId() {
        return bId;
    }

    public void setbId(Integer bId) {
        this.bId = bId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getBaseline() {
        return baseline;
    }

    public void setBaseline(String baseline) {
        this.baseline = baseline;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

