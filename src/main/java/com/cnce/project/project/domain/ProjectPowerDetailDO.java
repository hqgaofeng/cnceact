package com.cnce.project.project.domain;
import java.util.Date;

public class ProjectPowerDetailDO {
    //id
    private Integer pdId;
    // 项目名称
    private String proName;
    // 接口
    private String written;
    // 版本目的
    private String proPlan;
    // 模块名称
    private String modName;
    // 人员姓名
    private String staffName;
    // 实际人力
    private String actualManpower;
    // 工作内容
    private String workContent;
    //创建时间
    private Date createTime;

    public Integer getPdId() {
        return pdId;
    }

    public void setPdId(Integer pdId) {
        this.pdId = pdId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getWritten() {
        return written;
    }

    public void setWritten(String written) {
        this.written = written;
    }

    public String getProPlan() {
        return proPlan;
    }

    public void setProPlan(String proPlan) {
        this.proPlan = proPlan;
    }

    public String getModName() {
        return modName;
    }

    public void setModName(String modName) {
        this.modName = modName;
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

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

