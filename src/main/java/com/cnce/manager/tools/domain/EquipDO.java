package com.cnce.manager.tools.domain;

import java.io.Serializable;

public class EquipDO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;

    private Integer deptId;

    private String computerName;

    private String computerAlias;

    private Integer computerType;

    private Integer status;

    private String optSystem;

    private String computerIp;

    private String loginName;

    private String loginpass;

    private String attrArea;

    private String siteNumber;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getComputerAlias() {
        return computerAlias;
    }

    public void setComputerAlias(String computerAlias) {
        this.computerAlias = computerAlias;
    }

    public Integer getComputerType() {
        return computerType;
    }

    public void setComputerType(Integer computerType) {
        this.computerType = computerType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOptSystem() {
        return optSystem;
    }

    public void setOptSystem(String optSystem) {
        this.optSystem = optSystem;
    }

    public String getComputerIp() {
        return computerIp;
    }

    public void setComputerIp(String computerIp) {
        this.computerIp = computerIp;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginpass() {
        return loginpass;
    }

    public void setLoginpass(String loginpass) {
        this.loginpass = loginpass;
    }

    public String getAttrArea() {
        return attrArea;
    }

    public void setAttrArea(String attrArea) {
        this.attrArea = attrArea;
    }

    public String getSiteNumber() {
        return siteNumber;
    }

    public void setSiteNumber(String siteNumber) {
        this.siteNumber = siteNumber;
    }
}
