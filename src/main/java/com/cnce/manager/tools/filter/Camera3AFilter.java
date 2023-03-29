package com.cnce.manager.tools.filter;

import com.cnce.common.domain.ToolsInfo;

public class Camera3AFilter {

    /**
     * 相机镜头：前置front、后摄rear、广角wide、微距macro
     * @param cmaLen
     * @return
     */
    public static String getCameraLens(String cmaLen) {
        String value = "";
        switch (cmaLen){
            case "前摄":
                value = ToolsInfo.Camera3A.FRONT.toString();
                break;
            case "后摄":
                value = ToolsInfo.Camera3A.REAR.toString();
                break;
            case "广角":
                value = ToolsInfo.Camera3A.WIDE.toString();
                break;
            case "微距":
                value = ToolsInfo.Camera3A.MACRO.toString();
                break;
            default:
                break;
        }
        return value;
    }

    /**
     * 相机模式：单次连拍 1、循环进入2
     * @param model
     * @return
     */
    public static String getCameraModel(String model) {
        String value = "";
        switch (model){
            case "单次连拍":
                value = ToolsInfo.Camera3A.MODEL1.toString();
                break;
            case "循环进入":
                value = ToolsInfo.Camera3A.MODEL2.toString();
                break;
            default:
                break;
        }
        return value;
    }

    /**
     * 间隔时间：1s、2s、3s、4s、5s、6s、7s、8s、9s
     * @param intervals
     * @return
     */
    public static String getCameraIntervals(String intervals) {
        String value = "";
        switch (intervals){
            case "1s":
                value = ToolsInfo.Camera3A.SECOND1.toString();
                break;
            case "2s":
                value = ToolsInfo.Camera3A.SECOND2.toString();
                break;
            case "3s":
                value = ToolsInfo.Camera3A.SECOND3.toString();
                break;
            case "4s":
                value = ToolsInfo.Camera3A.SECOND4.toString();
                break;
            case "5s":
                value = ToolsInfo.Camera3A.SECOND5.toString();
                break;
            case "6s":
                value = ToolsInfo.Camera3A.SECOND6.toString();
                break;
            case "7s":
                value = ToolsInfo.Camera3A.SECOND7.toString();
                break;
            case "8s":
                value = ToolsInfo.Camera3A.SECOND8.toString();
                break;
            case "9s":
                value = ToolsInfo.Camera3A.SECOND9.toString();
                break;
            default:
                break;
        }
        return value;
    }


}
