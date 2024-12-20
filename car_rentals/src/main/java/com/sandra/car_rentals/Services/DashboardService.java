package com.sandra.car_rentals.Services;

import com.sandra.car_rentals.Repository.*;
import com.sandra.car_rentals.DTO.*;
import com.sandra.car_rentals.Model.Rentals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {
    @Autowired
    private RentalsRepository rentalsRepository;
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private CarsRepository carsRepository;

    public DashboardStatsDTO getDashboardStats() {
        DashboardStatsDTO stats = new DashboardStatsDTO();

        // Calculate Total Revenue (adjust based on your actual implementation)
        double totalRevenue = 0; // rentalsRepository.calculateTotalRevenue();
        stats.setTotalRevenue(totalRevenue);
        stats.setRevenueTrend(calculateTrend(totalRevenue));

        // Active Rentals (adjust based on your actual implementation)
        int activeRentals = 0; // rentalsRepository.countActiveRentals();
        stats.setActiveRentals(activeRentals);
        stats.setRentalsTrend(calculateTrend(activeRentals));

        // New Customers
        int newCustomers = customersRepository.countNewCustomersInCurrentMonth(null);
        stats.setNewCustomers(newCustomers);
        stats.setCustomersTrend(calculateTrend(newCustomers));

        // Pending Returns (adjust based on your actual implementation)
        int pendingReturns = 0; // rentalsRepository.countPendingReturns();
        stats.setPendingReturns(pendingReturns);
        stats.setReturnsTrend(calculateTrend(pendingReturns));

        return stats;
    }

    public List<ActivityDTO> getRecentActivities() {
        return rentalsRepository.findAll().stream()
            .map(rental -> new ActivityDTO(
                "rental", 
                "New rental for " + rental.getCar().getName(), 
                formatTimeAgo(rental.getRentalStartDate())
            ))
            .collect(Collectors.toList());
    }

  public List<UpcomingReturnDTO> getUpcomingReturns() {
    LocalDateTime endDate = LocalDateTime.now().plusHours(24); // Calculate the end date
    List<Rentals> upcomingRentals = rentalsRepository.findUpcomingReturns(endDate); // Fetch from DB

    return upcomingRentals.stream()
        .map(rental -> new UpcomingReturnDTO(
            rental.getName(), 
            rental.getCar().getName(), 
            formatTimeRemaining(rental.getRentalEndDate())
        ))
        .collect(Collectors.toList());
}

    private String calculateTrend(Number value) {
        // Implement trend calculation logic
        return value.doubleValue() > 0 ? "+12.5%" : "-5.8%";
    }

    private String formatTimeAgo(LocalDate date) {
        LocalDate now = LocalDate.now();
        long daysDifference = java.time.temporal.ChronoUnit.DAYS.between(date, now);
        
        if (daysDifference == 0) return "Today";
        if (daysDifference == 1) return "Yesterday";
        if (daysDifference < 0) return "Future date";
        return daysDifference + " days ago";
    }

    private String formatTimeRemaining(LocalDate date) {
        LocalDate now = LocalDate.now();
        long daysDifference = java.time.temporal.ChronoUnit.DAYS.between(now, date);
        
        if (daysDifference == 0) return "Today";
        if (daysDifference == 1) return "Tomorrow";
        if (daysDifference < 0) return "Overdue";
        return daysDifference + " days remaining";
    }
}