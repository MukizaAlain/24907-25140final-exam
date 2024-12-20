package com.sandra.car_rentals.Controller;

import com.sandra.car_rentals.Services.DashboardService;
import com.sandra.car_rentals.DTO.DashboardStatsDTO;
import com.sandra.car_rentals.DTO.ActivityDTO;
import com.sandra.car_rentals.DTO.UpcomingReturnDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        return ResponseEntity.ok(dashboardService.getDashboardStats());
    }

    @GetMapping("/recent-activities")
    public ResponseEntity<List<ActivityDTO>> getRecentActivities() {
        return ResponseEntity.ok(dashboardService.getRecentActivities());
    }

    @GetMapping("/upcoming-returns")
    public ResponseEntity<List<UpcomingReturnDTO>> getUpcomingReturns() {
        return ResponseEntity.ok(dashboardService.getUpcomingReturns());
    }
}