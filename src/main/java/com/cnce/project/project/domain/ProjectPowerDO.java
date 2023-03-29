package com.cnce.project.project.domain;
import java.util.Date;

public class ProjectPowerDO {
    //模块id
    private Integer peId;
    // 模块名称
    private String modName;
    // 项目名称
    private String proName;
    // 人力需求
    private String totalPower;
    // 人力安排
    private String planManpower;
    // 安排日期
    private String planDate;
    // 人员
    private String staffName;
    // 人员实际人力
    private String actualManpower;
    // 创建时间
    private Date createTime;

    public Integer getPeId() {
        return peId;
    }

    public void setPeId(Integer peId) {
        this.peId = peId;
    }

    public String getModName() {
        return modName;
    }

    public void setModName(String modName) {
        this.modName = modName;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getTotalPower() {
        return totalPower;
    }

    public void setTotalPower(String totalPower) {
        this.totalPower = totalPower;
    }

    public String getPlanManpower() {
        return planManpower;
    }

    public void setPlanManpower(String planManpower) {
        this.planManpower = planManpower;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getActualManpower() {
        return actualManpower;
    }

    public void setActualManpower(String actualManpower) {
        this.actualManpower = actualManpower;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

