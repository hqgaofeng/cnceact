package com.cnce.manager.manpower.domain;

public class ManPowerVO extends ManpowerDO{

    // 领域
    private String area;
    // 软测使用次数
    private Integer sUseTimes;
    // 硬测使用次数
    private Integer hUseTimes;
    // 软测节省人力
    private String sEffort;
    // 硬测节省人力
    private String hEffort;
    // 使用人数总计
    private String useTotal;
    // 节省人力总计
    private String effTotal;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getsUseTimes() {
        return sUseTimes;
    }

    public void setsUseTimes(Integer sUseTimes) {
        this.sUseTimes = sUseTimes;
    }

    public Integer gethUseTimes() {
        return hUseTimes;
    }

    public void sethUseTimes(Integer hUseTimes) {
        this.hUseTimes = hUseTimes;
    }

    public String getsEffort() {
        return sEffort;
    }

    public void setsEffort(String sEffort) {
        this.sEffort = sEffort;
    }

    public String gethEffort() {
        return hEffort;
    }

    public void sethEffort(String hEffort) {
        this.hEffort = hEffort;
    }

    public String getUseTotal() {
        return useTotal;
    }

    public void setUseTotal(String useTotal) {
        this.useTotal = useTotal;
    }

    public String getEffTotal() {
        return effTotal;
    }

    public void setEffTotal(String effTotal) {
        this.effTotal = effTotal;
    }
}
