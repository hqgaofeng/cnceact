package com.cnce.project.template.domain;
import java.util.Date;

public class TempSyncDO {
    //项目id
    private Integer proId;
    // Jira id
    private Integer JiraId;
    //项目名称
    private String pName;
    // 项目状态
    private Integer pStatus;
    // 地域
    private String region;
    //创建时间
    private Date createTime;

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public Integer getJiraId() {
        return JiraId;
    }

    public void setJiraId(Integer jiraId) {
        JiraId = jiraId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Integer getpStatus() {
        return pStatus;
    }

    public void setpStatus(Integer pStatus) {
        this.pStatus = pStatus;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

