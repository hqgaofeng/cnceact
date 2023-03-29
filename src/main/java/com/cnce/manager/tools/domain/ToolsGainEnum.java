package com.cnce.manager.tools.domain;


/**
 * 自动化工具模块收益
 */
public class ToolsGainEnum {

    // 多机升级（蓝区）
    public enum BlueMultiUpgrade {
        DOWNLOAD("下载", "0.25"),
        UPGRADE("升级", "0.25"),
        UPGRADEROOT("升级root", "1"),
        SKIPWIZAD("跳过开机向导", "1"),
        FULLPROCESS("全流程", "2.5"),
        TOTAL("合计", "2.5");

        private String name;
        private String value;

        private BlueMultiUpgrade(String name,String value) {
            this.name = name;
            this.value = value;
        }
        @Override
        public String toString() {
            return this.value;
        }
    }

    // 多机升级（自用区）
    public enum MultiUpgrade {
        UPGRADE("升级", "0.25"),
        UPGRADEROOT("升级root", "1.25"),
        SKIPWIZAD("跳过开机向导", "1"),
        TOTAL("合计", "2.5");

        private String name;
        private String value;

        private MultiUpgrade(String name,String value) {
            this.name = name;
            this.value = value;
        }
        @Override
        public String toString() {
            return this.value;
        }
    }

    // Top3000
    public enum TopApp {
        CHOOSEAPK("选择应用", "2.25"),
        IMPORTBTN("导入", "0.75"),
        QUERYBTN("查询", "1.5"),
        INSTALLBTN("安装应用", "1.5"),
        UNINSTALLBTN("卸载应用", "1.5"),
        APPTYPESEL("选择应用类别", "1.5"),
        APPAREA("选择细分领域", "1.5"),
        TOTAL("合计", "15");

        private String name;
        private String value;

        private TopApp(String name,String value) {
            this.name = name;
            this.value = value;
        }
        @Override
        public String toString() {
            return this.value;
        }
    }

    // 心跳&预测试（蓝区）
    public enum BlueHeartBeat {
        RUNTIMES("执行次数", "0.3"),
        CHOOSECASE("用例选择", "0.3"),
        STARTTEST("开始测试", "0.3"),
        CANCELTEST("取消测试", "0.1"),
        TOTAL("合计", "1");

        private String name;
        private String value;

        private BlueHeartBeat(String name,String value) {
            this.name = name;
            this.value = value;
        }
        @Override
        public String toString() {
            return this.value;
        }
    }

    // 心跳&预测试
    public enum HeartBeat {
        RUNTIMES("执行次数", "0.3"),
        CHOOSECASE("用例选择", "0.3"),
        STARTTEST("开始测试", "0.3"),
        CANCELTEST("取消测试", "0.1"),
        TOTAL("合计", "1");

        private String name;
        private String value;

        private HeartBeat(String name,String value) {
            this.name = name;
            this.value = value;
        }
        @Override
        public String toString() {
            return this.value;
        }
    }

    // Camera压测
    public enum CameraStress {
        CMAFEATURE("功能选择", "0.02"),
        CMALENS("测试镜头", "0.015"),
        CMAMODEL("相机模式", "0.015"),
        RESOLUTION("分辨率", "0.015"),
        TESTTIMES("测试次数", "0.015"),
        STARTBTN("开始", "0.025"),
        TOTAL("合计", "0.1");

        private String name;
        private String value;

        private CameraStress(String name,String value) {
            this.name = name;
            this.value = value;
        }
        @Override
        public String toString() {
            return this.value;
        }
    }

    // Camera3A
    public enum Camera3A {
        //TAF("TAF", "0.125"),
        AEAFCONTUE("单模式拍摄(20张)", "0.08125"),
        //AEAFCYCLE("AE/AF/AWB(20)循环", "0.24375"),
        AEAFMIX("双模式拍摄(40张)", "0.40625"),
        TOTAL("合计", "0.85625");

        private String name;
        private String value;

        private Camera3A(String name,String value) {
            this.name = name;
            this.value = value;
        }
        @Override
        public String toString() {
            return this.value;
        }
    }

    // Camera主观
    public enum CameraSub {
        STARTBTN("开始", "0.44"),
        TOTAL("合计", "0.44");

        private String name;
        private String value;

        private CameraSub(String name,String value) {
            this.name = name;
            this.value = value;
        }
        @Override
        public String toString() {
            return this.value;
        }
    }

    // Flare测试图片自动导出
    public enum Flare {
        STARTBTN("开始", "0.03"),
        TOTAL("合计", "0.03");

        private String name;
        private String value;

        private Flare(String name,String value) {
            this.name = name;
            this.value = value;
        }
        @Override
        public String toString() {
            return this.value;
        }
    }

    // WiFi干扰自动化
    public enum Wifi {
        SCENARIO("测试场景", "0.75"),
        NETWORK("测试网络", "0.75"),
        SPECTRUM("测试频段", "0.75"),
        CHANNEL("测试信道", "0.75"),
        SUBMIT("测试提交", "3");

        private String name;
        private String value;

        private Wifi(String name,String value) {
            this.name = name;
            this.value = value;
        }
        @Override
        public String toString() {
            return this.value;
        }
    }

}
