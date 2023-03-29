package com.cnce.common.jenkins;

import com.cnce.common.domain.ToolsInfo;

public class JenkinsFilter {

	/**
	 * 软测Node节点信息
	 * 
	 * @param device
	 * @return String
	 */
	public static String getSoftNode(String device) {
		String node = "";
		switch (device){
			case "执行机一":
				node = ToolsInfo.SoftDevice.DEVICE1.toString();
				break;
			case "执行机二":
				node = ToolsInfo.SoftDevice.DEVICE2.toString();
				break;
			case "执行机三":
				node = ToolsInfo.SoftDevice.DEVICE3.toString();
				break;
			case "执行机四":
				node = ToolsInfo.SoftDevice.DEVICE4.toString();
				break;
			default:
				break;
		}
		return node;
	}

	/**
	 * 硬测Node节点信息
	 *
	 * @param device
	 * @return String
	 */
	public static String getHardNode(String device) {
		String node = "";
		switch (device){
			case "执行机一":
				node = ToolsInfo.HardDevice.DEVICE1.toString();
				break;
			case "执行机二":
				node = ToolsInfo.HardDevice.DEVICE2.toString();
				break;
			case "执行机三":
				node = ToolsInfo.HardDevice.DEVICE3.toString();
				break;
			case "执行机四":
				node = ToolsInfo.HardDevice.DEVICE4.toString();
				break;
			case "执行机五":
				node = ToolsInfo.HardDevice.DEVICE5.toString();
				break;
			case "执行机六":
				node = ToolsInfo.HardDevice.DEVICE6.toString();
				break;
			case "执行机七":
				node = ToolsInfo.HardDevice.DEVICE7.toString();
				break;
			default:
				break;
		}
		return node;
	}

	public static Integer getResCode(String name) {
		Integer code = 0;
		switch (name){
			case "Building":
				code = 0;
				break;
			case "SUCCESS":
				code = 1;
				break;
			case "FAILURE":
				code = 0;
				break;
			case "ABORTED":
				code = 0;
				break;
			case "pending":
				code = 0;
				break;
			default:
				break;
		}
		return code;
	}

	public static String getjobName(Integer toolId) {
		String jobName = null;
		switch (toolId){
			case 1:
				// Camera样张分类
				jobName = JenkinsConstant.CMA_SUBJECTIVE;
				break;
			case 2:
				// Camera客观3A
				jobName = JenkinsConstant.CMA_OBJECTIVE;
				break;
			case 3:
				// 多机升级（蓝区）
				jobName = JenkinsConstant.B_MULTI_UPGRADE;
				break;
			case 4:
				// 多机升级
				jobName = JenkinsConstant.F_MULTI_UPGRADE;
				break;
			case 5:
				// 心跳&预测试（蓝区）
				jobName = JenkinsConstant.HB_7502_PRETEST;
				break;
			case 6:
				// 心跳&预测试
				jobName = JenkinsConstant.HB_7505_PRETEST;
				break;
			case 7:
				// Camera压测（蓝区）
				jobName = JenkinsConstant.CMA_STRENGTH;
				break;
			case 8:
				// TopApp安装卸载
				jobName = JenkinsConstant.TOP_APPLICATION;
				break;
			case 9:
				// Flare图片自动导出
				jobName = JenkinsConstant.FLARE_EXPORT;
				break;
			case 10:
				// wifi干扰
				jobName = JenkinsConstant.WIFI_INTERFERE;
				break;
			case 11:
				// wifi信令
				jobName = JenkinsConstant.WIFI_SIGNAL;
				break;
			case 12:
				// 基带开关机
				jobName = JenkinsConstant.PDU_ONOFF;
				break;
			default:
				break;
		}
		return jobName;
	}

	public static Integer getEstimated(Integer toolId) {
		Integer estimated = 0;
		switch (toolId){
			case 1:
				// Camera样张分类
				estimated = 480000;
				break;
			case 2:
				// Camera客观3A
				estimated = 600000;
				break;
			case 3:
				// 多机升级（蓝区）
				estimated = 1800000;
				break;
			case 4:
				// 多机升级
				estimated = 1800000;
				break;
			case 5:
				// 心跳&预测试（蓝区）
				estimated = 600000;
				break;
			case 6:
				// 心跳&预测试
				estimated = 600000;
				break;
			case 7:
				// Camera压测（蓝区）
				estimated = 1000000;
				break;
			case 8:
				// TopApp安装卸载
				estimated = 12000000;
				break;
			case 9:
				// Flare图片自动导出
				estimated = 30000;
				break;
			case 10:
				// WiFi干扰测试
				estimated = 14400000;
				break;
			case 11:
				// WiFi信令测试
				estimated = 10800000;
				break;
			case 12:
				// 基带开关机测试
				estimated = 1800000;
				break;
			default:
				break;
		}
		return estimated;
	}

	public static String getNodes(String name) {
		String nodeName = "";
		switch (name){
			case "10090199":
				nodeName = ToolsInfo.userNodes.HARD1.toString();
				break;
			case "10088988":
				nodeName = ToolsInfo.userNodes.HARD2.toString();;
				break;
			case "10098070":
				nodeName = ToolsInfo.userNodes.HARD3.toString();;
				break;
			case "10037974":
				nodeName = ToolsInfo.userNodes.HARD4.toString();;
				break;
			case "10078906":
				nodeName = ToolsInfo.userNodes.HARD5.toString();;
				break;
			case "10091627":
				nodeName = ToolsInfo.userNodes.HARD6.toString();;
				break;
			case "10028760":
				nodeName = ToolsInfo.userNodes.HARD7.toString();;
				break;
			default:
				break;
		}
		return nodeName;
	}


}
