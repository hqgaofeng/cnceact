package com.cnce.manager.tools.domain;

import java.io.Serializable;


/**
 * 工具管理
 *
 * @author Alan
 * @email chenfei917@163.com
 * @date 2017-09-27 14:28:36
 */
public class CameraStressTestingDO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer toolId;
    private String modelSort;

    private String modelName;

    private String resoName;


    public String getResoName() {
        return resoName;
    }

    public void setResoName(String resoName) {
        this.resoName = resoName;
    }

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

    public String getModelSort() {
        return modelSort;
    }

    public void setModelSort(String modelSort) {
        this.modelSort = modelSort;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }




}
