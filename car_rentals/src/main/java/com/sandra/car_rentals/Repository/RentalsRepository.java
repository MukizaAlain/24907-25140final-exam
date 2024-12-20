package com.sandra.car_rentals.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sandra.car_rentals.Model.Rentals;

@Repository
public interface RentalsRepository extends JpaRepository<Rentals, UUID> {

    List<Rentals> findByNameContainingIgnoreCase(String name);

 @Query("SELECT SUM(r.totalPrice) FROM Rentals r")
    double calculateTotalRevenue();

   

    

    @Query("SELECT r FROM Rentals r ORDER BY r.rentalStartDate DESC LIMIT 5")
    List<Rentals> findRecentActivities();

    @Query("SELECT r FROM Rentals r WHERE r.rentalEndDate BETWEEN CURRENT_TIMESTAMP AND :endDate")
    List<Rentals> findUpcomingReturns(LocalDateTime endDate);

}
