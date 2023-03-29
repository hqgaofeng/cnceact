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
public class ModulePowerChangDO {

    private int id;
    private String name;
    private String newValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
