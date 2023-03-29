package com.cnce.project.module.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: shichenglong
 * @DateTime: 2023/3/7 15:34
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModulePowerSaveDO {

    //meid
    private int meid;
    //操作
    private String operation;
    //new值
    private String newValue;
    //staffName
    private String userName;
    //proName
    private String proName;
    //remark
    private String remark;
    //modPower
    private String modPower;

    //未投入人力
    private String uncommitted;
    //实际人力
    private String actualManpower;
    //总计人力
    private String total;


}
