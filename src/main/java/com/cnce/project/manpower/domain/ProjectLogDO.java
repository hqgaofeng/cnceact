package com.cnce.project.manpower.domain;

import java.util.Date;

public class ProjectLogDO {

    private Integer id;

    private Integer userId;

    private String userName;

    private String editInfo;


    private String userDept;

    private Date createTime;

    public String getUserDept() {
        return userDept;
    }

    public void setUserDept(String userDept) {
        this.userDept = userDept;
    }


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

    public String getEditInfo() {
        return editInfo;
    }

    public void setEditInfo(String editInfo) {
        this.editInfo = editInfo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
