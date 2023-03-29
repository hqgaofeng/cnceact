package com.cnce.manager.profit.domain;

import java.util.Date;

public class ProfitDO {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer toolId;
    private String toolName;
    private String funName;
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

    public Integer getToolId() {
        return toolId;
    }

    public void setToolId(Integer toolId) {
        this.toolId = toolId;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getFunName() {
        return funName;
    }

    public void setFunName(String funName) {
        this.funName = funName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ProfitDO{" +
                "id=" + id +
                ", toolId=" + toolId +
                ", toolName='" + toolName + '\'' +
                ", funName='" + funName + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
