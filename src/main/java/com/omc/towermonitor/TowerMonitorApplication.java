package com.omc.towermonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TowerMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TowerMonitorApplication.class, args);
    }
}
