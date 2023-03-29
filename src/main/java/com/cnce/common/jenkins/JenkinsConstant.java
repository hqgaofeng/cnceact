package com.cnce.common.jenkins;


/**
 * Jenkins常量类
 */
public class JenkinsConstant {

    // 自用区
     public static String JENKINS_URL = "http://192.168.60.202:8080/";
    // 蓝区
//    public static String JENKINS_URL = "http://192.168.98.192:8080/";
    public static String JENKINS_USERNAME = "chenfei";
    public static String JENKINS_PASSWORD = "10095301";

    // 文件服务器
    public static String SERVER_IP = "192.168.60.202";
//    public static String SERVER_IP = "192.168.98.192";
    public static String LOGIN_USERNAME = "cnce";
    public static String LOGIN_PASSWORD = "123456";


    // Jenkins job name
    // Camera客观3A
    public static String CMA_OBJECTIVE = "CameraObjective";
    // Camera主观
    public static String CMA_SUBJECTIVE = "CameraSubjective";
    // Camera压测
    public static String CMA_STRENGTH = "CameraStrength";
    // 心跳预测试(7502)
    public static String HB_7502_PRETEST = "Hebeat7502Test";
    // 心跳预测试(7505)
    public static String HB_7505_PRETEST = "Hebeat7505Test";
    // TOP3000应用下载
    public static String TOP_APPLICATION = "TopApplication";
    // 多机升级工具（开放区）
    public static String F_MULTI_UPGRADE = "FreeMultiUpgrade";
    // 多机升级工具（蓝区）
    public static String B_MULTI_UPGRADE = "BlueMultiUpgrade";
    // Flare测试图片自动导出
    public static String FLARE_EXPORT = "FlareExport";
    // WIFI干扰自动化
    public static String WIFI_INTERFERE = "WifiInterfere";
    // WIFI信令自动化
    public static String WIFI_SIGNAL = "WifiSignal";
    // 电源开关机自动化
    public static String PDU_ONOFF = "PowerOnOff";
    // 4G主频干扰自动化
    public static String MACH_INTERFERE = "MachineInterfere";


}
