package com.omc.towermonitor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MalfunctioningSensor {
    private int sensorId;
    private String face;
    private double averageTemperature;
    private double deviation;
}