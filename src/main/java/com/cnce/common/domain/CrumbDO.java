package com.cnce.common.domain;

import java.io.Serializable;


public class CrumbDO implements Serializable {

    public String crumb;
    public String crumbRequestField;


    public String getCrumb() {
        return crumb;
    }

    public void setCrumb(String crumb) {
        this.crumb = crumb;
    }

    public String getCrumbRequestField() {
        return crumbRequestField;
    }

    public void setCrumbRequestField(String crumbRequestField) {
        this.crumbRequestField = crumbRequestField;
    }

}