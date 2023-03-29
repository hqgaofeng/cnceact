package com.cnce.project.template.domain;
import java.util.Date;

public class TemplateDO extends TempSyncDO {
    //项目id
    private Integer proId;
    // Jira id
    private Integer JiraId;
    //项目级别
    private Integer pLevel;
    //项目投入月份
    private String effectMonth;
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

    public Integer getpLevel() {
        return pLevel;
    }

    public void setpLevel(Integer pLevel) {
        this.pLevel = pLevel;
    }

    public String getEffectMonth() {
        return effectMonth;
    }

    public void setEffectMonth(String effectMonth) {
        this.effectMonth = effectMonth;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

