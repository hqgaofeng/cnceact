package com.cnce.manager.profit.domain;

import com.cnce.manager.tools.domain.ToolsGainEnum;

public class ProfitFilter {

    // Camera主观
    public static String getCMASubVal(String name) {
        String value = "";
        switch (name){
            case "开始":
                value = ToolsGainEnum.CameraSub.STARTBTN.toString();
                break;
            default:
                break;
        }
        return value;
    }

    // Camera客观
    public static String getCMA3AVal(String name) {
        String value = "";
        switch (name){
            case "单模式拍摄(20张)":
                value = ToolsGainEnum.Camera3A.AEAFCONTUE.toString();
                break;
            case "双模式拍摄(40张)":
                value = ToolsGainEnum.Camera3A.AEAFMIX.toString();
                break;
            default:
                break;
        }
        return value;
    }

    // 多机升级（蓝区）
    public static String getMuUpdVal(String name) {
        String value = "";
        switch (name){
            case "下载":
                value = ToolsGainEnum.BlueMultiUpgrade.DOWNLOAD.toString();
                break;
            case "升级":
                value = ToolsGainEnum.BlueMultiUpgrade.UPGRADE.toString();
                break;
            case "升级root":
                value = ToolsGainEnum.BlueMultiUpgrade.UPGRADEROOT.toString();
                break;
            case "跳过开机向导":
                value = ToolsGainEnum.BlueMultiUpgrade.SKIPWIZAD.toString();
                break;
            case "全流程":
                value = ToolsGainEnum.BlueMultiUpgrade.FULLPROCESS.toString();
                break;
            default:
                break;
        }
        return value;
    }

    // 多机升级（自用区）
    public static String getFreeMuUpdVal(String name) {
        String value = "";
        switch (name){
            case "升级":
                value = ToolsGainEnum.MultiUpgrade.UPGRADE.toString();
                break;
            case "升级root":
                value = ToolsGainEnum.MultiUpgrade.UPGRADEROOT.toString();
                break;
            case "跳过开机向导":
                value = ToolsGainEnum.MultiUpgrade.SKIPWIZAD.toString();
                break;
            default:
                break;
        }
        return value;
    }

    // TOP app安装卸载
    public static String getTopAppVal(String name) {
        String value = "";
        switch (name){
            case "选择应用":
                value = ToolsGainEnum.TopApp.CHOOSEAPK.toString();
                break;
            case "导入":
                value = ToolsGainEnum.TopApp.IMPORTBTN.toString();
                break;
            case "查询":
                value = ToolsGainEnum.TopApp.QUERYBTN.toString();
                break;
            case "安装应用":
                value = ToolsGainEnum.TopApp.INSTALLBTN.toString();
                break;
            case "卸载应用":
                value = ToolsGainEnum.TopApp.UNINSTALLBTN.toString();
                break;
            case "选择应用类别":
                value = ToolsGainEnum.TopApp.APPTYPESEL.toString();
                break;
            case "选择细分领域":
                value = ToolsGainEnum.TopApp.APPAREA.toString();
                break;
            default:
                break;
        }
        return value;
    }

    // 心跳&预测试（蓝区）
    public static String getBHeartVal(String name) {
        String value = "";
        switch (name){
            case "执行次数":
                value = ToolsGainEnum.BlueHeartBeat.RUNTIMES.toString();
                break;
            case "用例选择":
                value = ToolsGainEnum.BlueHeartBeat.CHOOSECASE.toString();
                break;
            case "开始测试":
                value = ToolsGainEnum.BlueHeartBeat.STARTTEST.toString();
                break;
            case "取消测试":
                value = ToolsGainEnum.BlueHeartBeat.CANCELTEST.toString();
                break;
            default:
                break;
        }
        return value;
    }

    // 心跳&预测试
    public static String getHeartVal(String name) {
        String value = "";
        switch (name){
            case "执行次数":
                value = ToolsGainEnum.HeartBeat.RUNTIMES.toString();
                break;
            case "用例选择":
                value = ToolsGainEnum.HeartBeat.CHOOSECASE.toString();
                break;
            case "开始测试":
                value = ToolsGainEnum.HeartBeat.STARTTEST.toString();
                break;
            case "取消测试":
                value = ToolsGainEnum.HeartBeat.CANCELTEST.toString();
                break;
            default:
                break;
        }
        return value;
    }

    // Camera压测
    public static String getCMAStressVal(String name) {
        String value = "";
        switch (name){
            case "功能选择":
                value = ToolsGainEnum.CameraStress.CMAFEATURE.toString();
                break;
            case "测试镜头":
                value = ToolsGainEnum.CameraStress.CMALENS.toString();
                break;
            case "相机模式":
                value = ToolsGainEnum.CameraStress.CMAMODEL.toString();
                break;
            case "分辨率":
                value = ToolsGainEnum.CameraStress.RESOLUTION.toString();
                break;
            case "测试次数":
                value = ToolsGainEnum.CameraStress.TESTTIMES.toString();
                break;
            case "开始":
                value = ToolsGainEnum.CameraStress.STARTBTN.toString();
                break;
            default:
                break;
        }
        return value;
    }

    // Flare
    public static String getFlareVal(String name) {
        String value = "";
        switch (name){
            case "开始":
                value = ToolsGainEnum.Flare.STARTBTN.toString();
                break;
            default:
                break;
        }
        return value;
    }

    // WIFI干扰
    public static String getWifiVal(String name) {
        String value = "";
        switch (name){
            case "测试场景":
                value = ToolsGainEnum.Wifi.SCENARIO.toString();
                break;
            case "测试网络":
                value = ToolsGainEnum.Wifi.NETWORK.toString();
                break;
            case "测试频段":
                value = ToolsGainEnum.Wifi.SPECTRUM.toString();
                break;
            case "测试信道":
                value = ToolsGainEnum.Wifi.CHANNEL.toString();
                break;
            case "测试提交":
                value = ToolsGainEnum.Wifi.CHANNEL.toString();
                break;
            default:
                break;
        }
        return value;
    }

}
