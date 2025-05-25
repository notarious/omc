package com.omc.towermonitor.service;

import com.omc.towermonitor.model.AggregatedReading;
import com.omc.towermonitor.model.MalfunctioningSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);

    public ReportService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AggregatedReading> getHourlyReport() {
        String sql = "SELECT face, (timestamp / 3600) * 3600 AS hour, AVG(temperature) AS avg_temp " +
                "FROM sensor_data " +
                "WHERE timestamp >= strftime('%s','now') - 7 * 24 * 3600 " +
                "GROUP BY face, hour ORDER BY hour DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new AggregatedReading(
                        rs.getString("face"),
                        rs.getLong("hour"),
                        rs.getDouble("avg_temp")
                )
        );
    }

    public List<MalfunctioningSensor> getMalfunctioningSensors() {
        String sensorSql = "SELECT sensor_id, face, AVG(temperature) AS avg_temp FROM sensor_data " +
                "WHERE timestamp >= strftime('%s','now') - 3600 GROUP BY sensor_id, face";
        String faceSql = "SELECT face, AVG(temperature) AS avg_temp FROM sensor_data " +
                "WHERE timestamp >= strftime('%s','now') - 3600 GROUP BY face";

        Map<String, Double> faceAvg = jdbcTemplate.query(faceSql, rs -> {
            Map<String, Double> map = new HashMap<>();
            while (rs.next()) {
                map.put(rs.getString("face"), rs.getDouble("avg_temp"));
            }
            return map;
        });

        return jdbcTemplate.query(sensorSql, (rs, rowNum) -> {
            int id = rs.getInt("sensor_id");
            String face = rs.getString("face");
            double avg = rs.getDouble("avg_temp");
            double faceAvgVal = faceAvg.getOrDefault(face, avg);
            double deviation = Math.abs(avg - faceAvgVal) / faceAvgVal;

            if (deviation > 0.2) {
                return new MalfunctioningSensor(id, face, avg, deviation);
            }
            return null;
        }).stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 3600000)
    public void logMalfunctioningSensors() {
        for (MalfunctioningSensor s : getMalfunctioningSensors()) {
            logger.warn("Malfunctioning sensor: id={}, face={}, avgTemp={}, deviation={}",
                    s.getSensorId(), s.getFace(), s.getAverageTemperature(), s.getDeviation());
        }
    }
}
