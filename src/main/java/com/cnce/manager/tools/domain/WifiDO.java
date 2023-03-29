package com.cnce.manager.tools.domain;

import java.util.Date;
import java.util.List;

/**
 * WIFI干扰自动化
 */
public class WifiDO {
    // 项目名称
    private String projectName;
    // 项目阶段
    private String testStage;
    // 项目方案
    private String testPlan;
    // 测试工程师
    private String engineer;
    // 测试设备
    private String device;
    // 测试时间
    private Date testTime;
    // 初始电平值
    private String initLevel;
    // UE Power
    private String power;
    // 线损
    private String lineLoss;
    // 测试场景
    private String scenario;
    // 测试网络
    private String network;
    // 测试频段
    private String spectrum;
    // 测试信道
    private String channel;
    // 选择设备
    private String equipName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public Date getTestTime() {
        return testTime;
    }

    public void setTestTime(Date testTime) {
        this.testTime = testTime;
    }

    public String getInitLevel() {
        return initLevel;
    }

    public void setInitLevel(String initLevel) {
        this.initLevel = initLevel;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getLineLoss() {
        return lineLoss;
    }

    public void setLineLoss(String lineLoss) {
        this.lineLoss = lineLoss;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getSpectrum() {
        return spectrum;
    }

    public void setSpectrum(String spectrum) {
        this.spectrum = spectrum;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getEquipName() {
        return equipName;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    @Override
    public String toString() {
        return "WifiDO{" +
                "projectName='" + projectName + '\'' +
                ", testStage='" + testStage + '\'' +
                ", testPlan='" + testPlan + '\'' +
                ", engineer='" + engineer + '\'' +
                ", device='" + device + '\'' +
                ", testTime='" + testTime + '\'' +
                ", initLevel='" + initLevel + '\'' +
                ", power='" + power + '\'' +
                ", lineLoss='" + lineLoss + '\'' +
                ", scenario=" + scenario +
                ", network=" + network +
                ", spectrum=" + spectrum +
                ", channel=" + channel +
                ", equipName='" + equipName + '\'' +
                '}';
    }
}
