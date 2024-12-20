package com.sandra.car_rentals.DTO;

public class UpcomingReturnDTO {
    private String customer;
    private String car;
    private String time;

    // Default constructor
    public UpcomingReturnDTO() {
    }

    // Parameterized constructor
    public UpcomingReturnDTO(String customer, String car, String time) {
        this.customer = customer;
        this.car = car;
        this.time = time;
    }

    // Getters and Setters
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}