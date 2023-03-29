package com.cnce.project.manpower.domain;

import com.cnce.project.template.domain.TemplateDO;
import io.swagger.models.auth.In;

import java.util.*;

public class PowerDO extends TemplateDO {
    // 主键id
    private Integer id;
    // 用户id
    private Integer userId;
    // 用户名
    private String userName;
    // 人员账号
    private String account;
    // 部门id
    private Integer deptId;
    // 部门名称
    private String deptName;
    // 工号
    private Integer jobNo;
    //email
    private String email;
    // 人员状态
    private Integer userStatus;
    // 人力
    private String proManPower;
    //项目人力
    private String manPower;
    //人力总和
    private String totalManpower;
    // 维护项目
    private String maintainItems;
    // 平台投入
    private String investInTruck;
    // 实习生培训阶段
    private String traineeTrain;
    // 未投入
    private String nonInput;
    // 未入职
    private String nonEntry;
    // 产假
    private String maternity;
    // 长假
    private String longHoliday;
    //备注
    private String remark;
    // 创建时间
    private Date createTime;
    // 动态的项目
    Map<String, String> proMap = new HashMap<String, String>();

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getJobNo() {
        return jobNo;
    }

    public void setJobNo(Integer jobNo) {
        this.jobNo = jobNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getProManPower() {
        return proManPower;
    }

    public void setProManPower(String proManPower) {
        this.proManPower = proManPower;
    }

    public String getManPower() {
        return manPower;
    }

    public void setManPower(String manPower) {
        this.manPower = manPower;
    }

    public String getTotalManpower() {
        return totalManpower;
    }

    public void setTotalManpower(String totalManpower) {
        this.totalManpower = totalManpower;
    }

    public String getMaintainItems() {
        return maintainItems;
    }

    public void setMaintainItems(String maintainItems) {
        this.maintainItems = maintainItems;
    }

    public String getInvestInTruck() {
        return investInTruck;
    }

    public void setInvestInTruck(String investInTruck) {
        this.investInTruck = investInTruck;
    }

    public String getTraineeTrain() {
        return traineeTrain;
    }

    public void setTraineeTrain(String traineeTrain) {
        this.traineeTrain = traineeTrain;
    }

    public String getNonInput() {
        return nonInput;
    }

    public void setNonInput(String nonInput) {
        this.nonInput = nonInput;
    }

    public String getNonEntry() {
        return nonEntry;
    }

    public void setNonEntry(String nonEntry) {
        this.nonEntry = nonEntry;
    }

    public String getMaternity() {
        return maternity;
    }

    public void setMaternity(String maternity) {
        this.maternity = maternity;
    }

    public String getLongHoliday() {
        return longHoliday;
    }

    public void setLongHoliday(String longHoliday) {
        this.longHoliday = longHoliday;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Map<String, String> getProMap() {
        return proMap;
    }

    public void setProMap(Map<String, String> proMap) {
        this.proMap = proMap;
    }

    @Override
    public String toString() {
        return "PowerDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", account='" + account + '\'' +
                ", deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                ", jobNo=" + jobNo +
                ", email='" + email + '\'' +
                ", userStatus=" + userStatus +
                ", proManPower='" + proManPower + '\'' +
                ", manPower='" + manPower + '\'' +
                ", totalManpower='" + totalManpower + '\'' +
                ", maintainItems='" + maintainItems + '\'' +
                ", investInTruck='" + investInTruck + '\'' +
                ", traineeTrain='" + traineeTrain + '\'' +
                ", nonInput='" + nonInput + '\'' +
                ", nonEntry='" + nonEntry + '\'' +
                ", maternity='" + maternity + '\'' +
                ", longHoliday='" + longHoliday + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", proMap=" + proMap +
                '}';
    }
}
