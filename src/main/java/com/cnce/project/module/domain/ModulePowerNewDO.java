package com.cnce.project.module.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: shichenglong
 * @DateTime: 2023/3/12 23:34
 * @Description: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModulePowerNewDO {
    //模块id
    private Integer meId;
    // 模块名称
    private String modName;
    //modelPower
    private String modPower;
    // 模块名称
    private String planDate;
    //创建时间
    private Date createTime;
}
