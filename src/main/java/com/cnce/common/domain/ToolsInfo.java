package com.cnce.common.domain;


public class ToolsInfo {

    public enum userNodes {
        HARD1("10090199", "hard-test-曹星星"),
        HARD2("10088988", "hard-test-朱玲娜"),
        HARD3("10098070", "hard-test-刘月"),
        HARD4("10037974", "hard-test-宋思甜"),
        HARD5("10078906", "hard-test-李晗怡"),
        HARD6("10091627", "hard-test-杨雪婷"),
        HARD7("10028760", "hard-test-张青青");

        private String type;
        private String name;

        private userNodes(String type,String name) {
            this.type = type;
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public enum SoftDevice {
        DEVICE1("执行机一", "soft_test1"),
        DEVICE2("执行机二", "soft_test2"),
        DEVICE3("执行机三", "soft_test3"),
        DEVICE4("执行机四", "soft_test4");

        private String type;
        private String name;

        private SoftDevice(String type,String name) {
            this.type = type;
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public enum HardDevice {
        DEVICE1("执行机一", "hard_test1"),
        DEVICE2("执行机二", "hard_test2"),
        DEVICE3("执行机三", "hard_test3"),
        DEVICE4("执行机四", "hard_test4"),
        DEVICE5("执行机五", "hard_test5"),
        DEVICE6("执行机六", "hard_test6"),
        DEVICE7("执行机七", "hard_test7");

        private String type;
        private String name;

        private HardDevice(String type,String name) {
            this.type = type;
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public enum Camera3A {
        FRONT("前摄", "front"),
        REAR("后摄", "rear"),
        WIDE("广角", "wide"),
        MACRO("微距", "macro"),

        MODEL1("单次连拍", "1"),
        MODEL2("循环进入", "2"),

        SECOND1("1s", "1"),
        SECOND2("2s", "2"),
        SECOND3("3s", "3"),
        SECOND4("4s", "4"),
        SECOND5("5s", "5"),
        SECOND6("6s", "6"),
        SECOND7("7s", "7"),
        SECOND8("8s", "8"),
        SECOND9("9s", "9");


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

}
