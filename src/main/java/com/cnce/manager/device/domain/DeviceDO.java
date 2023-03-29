package com.cnce.manager.device.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 测试设备信息
 */
public class DeviceDO implements Serializable {

    private Integer deviceId;
    private Integer deptId;
    private String computerName;
    private String computerAlias;
    private Integer computerType;
    private Integer status;
    private String optSystem;
    private String ip;
    private String loginName;
    private String loginPass;
    private String attrArea;
    private String siteNum;
    private Integer userId;
    private String userName;
    private String belonger;
    private String description;
    private Date createTime;

    public Integer getId() {
        return deviceId;
    }

    public void setId(Integer id) {
        this.deviceId = id;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass;
    }

    public String getAttrArea() {
        return attrArea;
    }

    public void setAttrArea(String attrArea) {
        this.attrArea = attrArea;
    }

    public String getSiteNum() {
        return siteNum;
    }

    public void setSiteNum(String siteNum) {
        this.siteNum = siteNum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBelonger() {
        return belonger;
    }

    public void setBelonger(String belonger) {
        this.belonger = belonger;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "DeviceDO{" +
                "id=" + deviceId +
                ", deptId=" + deptId +
                ", computerName='" + computerName + '\'' +
                ", computerAlias='" + computerAlias + '\'' +
                ", computerType=" + computerType +
                ", status=" + status +
                ", optSystem='" + optSystem + '\'' +
                ", ip='" + ip + '\'' +
                ", loginName='" + loginName + '\'' +
                ", loginPass='" + loginPass + '\'' +
                ", attrArea='" + attrArea + '\'' +
                ", siteNum='" + siteNum + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", belonger='" + belonger + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
