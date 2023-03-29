package com.cnce.manager.tools.domain;

import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * WIFI信令
 */
public class WifiSignalDO {
    // 项目名称
    private String proName;
    // 项目阶段
    private String testStage;
    // 项目方案
    private String testPlan;
    // 测试工程师
    private String engineer;
    // 测试设备
    private String device;
    // 测试时间
    private Date beginTime;
    // 期望功率
    private String expPower;
    // 2.4G线损
    private String cbl4Loss;
    // 5G线损
    private String cbl5Loss;
    // 测试场景
    List<String> scene = new ArrayList<>();
    // 选择设备
    private String equipName;
    // 制式（2.4G/5G/2.4G&5G）
    // private String network;
    // 频段 (Map key为制式， value为频段)
    // List<Map<String, String>> band = new ArrayList<>();
    // 速率（key为频段， value为速率）
    // List<Map<String, List<String>>> speed = new ArrayList<>();
    // 信道（key为速率， value为信道）
    // List<Map<String, List<String>>> channel = new ArrayList<>();
    // 多制式选择对象
    JSONObject jsonb = new JSONObject();

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getTestStage() {
        return testStage;
    }

    public void setTestStage(String testStage) {
        this.testStage = testStage;
    }

    public String getTestPlan() {
        return testPlan;
    }

    public void setTestPlan(String testPlan) {
        this.testPlan = testPlan;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public String getExpPower() {
        return expPower;
    }

    public void setExpPower(String expPower) {
        this.expPower = expPower;
    }

    public String getCbl4Loss() {
        return cbl4Loss;
    }

    public void setCbl4Loss(String cbl4Loss) {
        this.cbl4Loss = cbl4Loss;
    }

    public String getCbl5Loss() {
        return cbl5Loss;
    }

    public void setCbl5Loss(String cbl5Loss) {
        this.cbl5Loss = cbl5Loss;
    }

    public List<String> getScene() {
        return scene;
    }

    public void setScene(List<String> scene) {
        this.scene = scene;
    }

    public String getEquipName() {
        return equipName;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public JSONObject getJsonb() {
        return jsonb;
    }

    public void setJsonb(JSONObject jsonb) {
        this.jsonb = jsonb;
    }
}
