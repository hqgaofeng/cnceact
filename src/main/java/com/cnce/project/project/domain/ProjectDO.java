package com.cnce.project.project.domain;
import java.util.Date;

public class ProjectDO {
    //id
    private Integer pId;
    // 项目名称
    private String proName;
    //创建时间
    private Date createTime;

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

