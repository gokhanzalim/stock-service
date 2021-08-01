package com.gokhan.stockservice.controller;

import com.gokhan.stockservice.model.request.StatisticsRequest;
import com.gokhan.stockservice.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistic")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @PostMapping("/monthly")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getStatisticMonthly(@RequestBody StatisticsRequest request){
        return ResponseEntity.ok(statisticsService.getStatisticMonthly(request));
    }

    @GetMapping("/allofcurrentmonth")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getStatisticMonthly(){
        return ResponseEntity.ok(statisticsService.getAllOfCurrentMonth());
    }

    @GetMapping("/allorderstatisticspermonth")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllOrderStatisticsPerMonth(){
        return ResponseEntity.ok(statisticsService.getAllOrderStatisticsPerMonth());
    }
}
