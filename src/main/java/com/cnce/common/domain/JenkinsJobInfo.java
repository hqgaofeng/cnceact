package com.cnce.common.domain;


/**
 * Jenkins Job与ApiToken信息
 */
public class JenkinsJobInfo {

    public enum JobName {
        JOB1("CameraObjective", "CameraObjective"),
        JOB2("CameraSubjective", "CameraSubjective"),
        JOB3("CameraStrength", "CameraStrength"),
        JOB4("Hebeat7502Test", "Hebeat7502Test"),
        JOB5("Hebeat7505Test", "Hebeat7505Test"),
        JOB6("TopApplication", "TopApplication"),
        JOB7("FreeMultiUpgrade", "FreeMultiUpgrade"),
        JOB8("BlueMultiUpgrade", "BlueMultiUpgrade"),
        JOB9("FlareExport", "FlareExport"),
        JOB10("WifiInterfere", "WifiInterfere"),
        JOB11("WifiSignal", "WifiSignal"),
        JOB12("PowerOnOff", "PowerOnOff"),
        JOB13("MachineInterfere", "MachineInterfere");

        private String name;
        private String apiToken;

        private JobName(String name,String apiToken) {
            this.name = name;
            this.apiToken = apiToken;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public enum Job{
        JOB1, JOB2, JOB3, JOB4, JOB5, JOB6, JOB7, JOB8, JOB9, JOB10, JOB11, JOB12;
    }

}
