package com.cnce.project.module.domain;
import java.util.Date;

public class ModuleDO {
    //模块id
    private Integer mId;
    // 模块名称
    private String modName;
    //创建时间
    private Date createTime;

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getModName() {
        return modName;
    }

    public void setModName(String modName) {
        this.modName = modName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

