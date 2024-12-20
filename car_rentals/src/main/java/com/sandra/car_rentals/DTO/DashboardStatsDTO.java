package com.sandra.car_rentals.DTO;

public class DashboardStatsDTO {
    private double totalRevenue;
    private int activeRentals;
    private int newCustomers;
    private int pendingReturns;
    private String revenueTrend;
    private String rentalsTrend;
    private String customersTrend;
    private String returnsTrend;

    // Default constructor
    public DashboardStatsDTO() {
    }

    // Getters and Setters
    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public int getActiveRentals() {
        return activeRentals;
    }

    public void setActiveRentals(int activeRentals) {
        this.activeRentals = activeRentals;
    }

    public int getNewCustomers() {
        return newCustomers;
    }

    public void setNewCustomers(int newCustomers) {
        this.newCustomers = newCustomers;
    }

    public int getPendingReturns() {
        return pendingReturns;
    }

    public void setPendingReturns(int pendingReturns) {
        this.pendingReturns = pendingReturns;
    }

    public String getRevenueTrend() {
        return revenueTrend;
    }

    public void setRevenueTrend(String revenueTrend) {
        this.revenueTrend = revenueTrend;
    }

    public String getRentalsTrend() {
        return rentalsTrend;
    }

    public void setRentalsTrend(String rentalsTrend) {
        this.rentalsTrend = rentalsTrend;
    }

    public String getCustomersTrend() {
        return customersTrend;
    }

    public void setCustomersTrend(String customersTrend) {
        this.customersTrend = customersTrend;
    }

    public String getReturnsTrend() {
        return returnsTrend;
    }

    public void setReturnsTrend(String returnsTrend) {
        this.returnsTrend = returnsTrend;
    }
}