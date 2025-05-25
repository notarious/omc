package com.omc.towermonitor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorData {
    private int sensorId;
    private String face;
    private long timestamp;
    private double temperature;
}
