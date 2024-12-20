package com.sandra.car_rentals.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandra.car_rentals.Model.Branch;
import com.sandra.car_rentals.Model.Cars;
import com.sandra.car_rentals.Model.Customers;
import com.sandra.car_rentals.Model.Rentals;
import com.sandra.car_rentals.Model.SearchResults;
import com.sandra.car_rentals.Model.User;
import com.sandra.car_rentals.Repository.BranchRepository;
import com.sandra.car_rentals.Repository.CarsRepository;
import com.sandra.car_rentals.Repository.CustomersRepository;
import com.sandra.car_rentals.Repository.RentalsRepository;
import com.sandra.car_rentals.Repository.UserRepository;

import java.util.List;

@Service
public class SearchService {

    private final UserRepository userRepository;
    private final CarsRepository carsRepository;
    private final RentalsRepository rentalsRepository;
    private final BranchRepository branchRepository;
    private final CustomersRepository customersRepository;

    @Autowired
    public SearchService(
        UserRepository userRepository,
        CarsRepository carsRepository,
        RentalsRepository rentalsRepository,
        BranchRepository branchRepository,
        CustomersRepository customersRepository
    ) {
        this.userRepository = userRepository;
        this.carsRepository = carsRepository;
        this.rentalsRepository = rentalsRepository;
        this.branchRepository = branchRepository;
        this.customersRepository = customersRepository;
    }

    public SearchResults search(String query) {
        List<User> users = userRepository.findByUsernameContainingIgnoreCase(query);
        List<Cars> cars = carsRepository.findByNameContainingIgnoreCase(query);
        List<Rentals> rentals = rentalsRepository.findByNameContainingIgnoreCase(query);
        
        // Use the alternative method if the containing method doesn't work
        List<Branch> branches = branchRepository.searchByName(query);
        
        List<Customers> customers = customersRepository.findByNameContainingIgnoreCase(query);

        return new SearchResults(users, cars, rentals, branches, customers);
    }
}