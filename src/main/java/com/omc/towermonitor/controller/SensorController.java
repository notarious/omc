package com.omc.towermonitor.controller;

import com.omc.towermonitor.model.SensorData;
import com.omc.towermonitor.service.SensorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensor-data")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping
    public ResponseEntity<String> receiveSensorData(@RequestBody SensorData data) {
        sensorService.saveSensorData(data);
        return ResponseEntity.ok("Sensor data received");
    }
}