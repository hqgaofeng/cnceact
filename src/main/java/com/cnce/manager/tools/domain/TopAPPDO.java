package com.cnce.manager.tools.domain;

import java.io.Serializable;


/**
 * 工具管理
 *
 * @author Alan
 * @email chenfei917@163.com
 * @date 2017-09-27 14:28:36
 */
public class TopAPPDO implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer id;

    private String appPackage;

    private String name;

    private String type;

    private String appField;

    private String appRank;

    public Integer getToolId() {
        return toolId;
    }

    public void setToolId(Integer toolId) {
        this.toolId = toolId;
    }

    private Integer toolId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAppField() {
        return appField;
    }

    public void setAppField(String appField) {
        this.appField = appField;
    }

    public String getAppRank() {
        return appRank;
    }

    public void setAppRank(String appRank) {
        this.appRank = appRank;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }


}
