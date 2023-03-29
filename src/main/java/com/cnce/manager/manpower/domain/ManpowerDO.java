package com.cnce.manager.manpower.domain;

import java.io.Serializable;
import java.util.Date;

public class ManpowerDO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer toolId;
    private String toolName;
    private String useDept;
    private String funName;
    private Date startUseTime;
    private Date endUseTime;
    private String useTime;
    private String manPower;
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

    public String getUseDept() {
        return useDept;
    }

    public void setUseDept(String useDept) {
        this.useDept = useDept;
    }

    public Date getStartUseTime() {
        return startUseTime;
    }

    public void setStartUseTime(Date startUseTime) {
        this.startUseTime = startUseTime;
    }

    public Date getEndUseTime() {
        return endUseTime;
    }

    public void setEndUseTime(Date endUseTime) {
        this.endUseTime = endUseTime;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getManPower() {
        return manPower;
    }

    public void setManPower(String manPower) {
        this.manPower = manPower;
    }

    public String getFunName() {
        return funName;
    }

    public void setFunName(String funName) {
        this.funName = funName;
    }

    @Override
    public String toString() {
        return "ManpowerDO{" +
                "id=" + id +
                ", toolId=" + toolId +
                ", toolName='" + toolName + '\'' +
                ", useDept='" + useDept + '\'' +
                ", funName='" + funName + '\'' +
                ", startUseTime=" + startUseTime +
                ", endUseTime=" + endUseTime +
                ", useTime='" + useTime + '\'' +
                ", manPower='" + manPower + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
