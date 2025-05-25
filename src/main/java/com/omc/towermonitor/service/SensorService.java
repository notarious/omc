package com.omc.towermonitor.service;

import com.omc.towermonitor.model.SensorData;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SensorService {

    private final JdbcTemplate jdbcTemplate;

    public SensorService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveSensorData(SensorData data) {
        String sql = "INSERT INTO sensor_data (sensor_id, face, timestamp, temperature) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, data.getSensorId(), data.getFace(), data.getTimestamp(), data.getTemperature());
    }
}