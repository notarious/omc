package com.omc.towermonitor.controller;

import com.omc.towermonitor.model.AggregatedReading;
import com.omc.towermonitor.model.MalfunctioningSensor;
import com.omc.towermonitor.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/hourly")
    public List<AggregatedReading> getHourlyTemperatures() {
        return reportService.getHourlyReport();
    }

    @GetMapping("/malfunctioning")
    public List<MalfunctioningSensor> getMalfunctioningSensors() {
        return reportService.getMalfunctioningSensors();
    }
}