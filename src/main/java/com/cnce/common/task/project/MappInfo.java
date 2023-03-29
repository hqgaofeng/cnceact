package com.cnce.common.task.project;


public class MappInfo {

    public enum region {
        XA("XA", "西安"),
        BJ("BJ", "北京");

        private String type;
        private String name;

        private region(String type,String name) {
            this.type = type;
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public enum userStatus {
        ZS("正式员工", "0"),
        LZ("离职员工", "1"),
        SY("试用员工", "2"),
        WZ("未知员工", "3"),
        SX("实习员工", "4");

        private String type;
        private String value;

        private userStatus(String type, String value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

}
