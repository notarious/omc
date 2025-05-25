package com.omc.towermonitor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AggregatedReading {
    private String face;
    private long hour;
    private double averageTemperature;
}