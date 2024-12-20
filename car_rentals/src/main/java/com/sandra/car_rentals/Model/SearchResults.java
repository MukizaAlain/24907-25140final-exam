package com.sandra.car_rentals.Model;

import java.util.List;

public class SearchResults {
    private List<User> users;
    private List<Cars> cars;
    private List<Rentals> rentals;
    private List<Branch> branches;
    private List<Customers> customers;

    public SearchResults(List<User> users, List<Cars> cars, List<Rentals> rentals, List<Branch> branches, List<Customers> customers) {
        this.users = users;
        this.cars = cars;
        this.rentals = rentals;
        this.branches = branches;
        this.customers = customers;
    }

    // Getters and Setters
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Cars> getCars() {
        return cars;
    }

    public void setCars(List<Cars> cars) {
        this.cars = cars;
    }

    public List<Rentals> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rentals> rentals) {
        this.rentals = rentals;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    public List<Customers> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customers> customers) {
        this.customers = customers;
    }
}