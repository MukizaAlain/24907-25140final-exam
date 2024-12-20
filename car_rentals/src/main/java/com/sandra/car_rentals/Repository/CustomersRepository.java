package com.sandra.car_rentals.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sandra.car_rentals.Model.Customers;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, UUID> {
    boolean existsByNameAndEmail(String name, String email);

    Customers findByNameAndEmail(String name, String email);

    boolean existsByEmail(String email);

    @Query("SELECT COUNT(c) FROM Customers c WHERE c.createdAt >= :date")
    int countNewCustomersInCurrentMonth(@Param("date") LocalDateTime date);

    List<Customers> findByNameContainingIgnoreCase(String name);

}
