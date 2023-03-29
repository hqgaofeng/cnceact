package com.cnce.system.domain;

import java.io.Serializable;
import java.util.Date;

public class ManagerDO implements Serializable {

    private  Integer  id;

    private  Integer reqId;

    private Integer deptId;

    private String name;

    private String platform;

    private String  tProject;

    private String tVersion;

    private Integer status;

    private String useDept;

    private String tUseArea;

    private String tDescribe;

    private String toolPath;

    private Date dueTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReqId() {
        return reqId;
    }

    public void setReqId(Integer reqId) {
        this.reqId = reqId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String gettProject() {
        return tProject;
    }

    public void settProject(String tProject) {
        this.tProject = tProject;
    }

    public String gettVersion() {
        return tVersion;
    }

    public void settVersion(String tVersion) {
        this.tVersion = tVersion;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUseDept() {
        return useDept;
    }

    public void setUseDept(String useDept) {
        this.useDept = useDept;
    }

    public String gettUseArea() {
        return tUseArea;
    }

    public void settUseArea(String tUseArea) {
        this.tUseArea = tUseArea;
    }

    public String gettDescribe() {
        return tDescribe;
    }

    public void settDescribe(String tDescribe) {
        this.tDescribe = tDescribe;
    }

    public String getToolPath() {
        return toolPath;
    }

    public void setToolPath(String toolPath) {
        this.toolPath = toolPath;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


}
