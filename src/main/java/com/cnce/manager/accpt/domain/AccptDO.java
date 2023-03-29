package com.cnce.manager.accpt.domain;

import java.util.Date;

public class AccptDO {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer toolId;

    private String toolName;

    private Integer userId;

    private String userName;

    private String opinion;

    private Date accptTime;


    private Integer accptResult;

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

    public Date getAccptTime() {
        return accptTime;
    }

    public void setAccptTime(Date accptTime) {
        this.accptTime = accptTime;
    }



    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Integer getAccptResult() {
        return accptResult;
    }

    public void setAccptResult(Integer accptResult) {
        this.accptResult = accptResult;
    }

}
