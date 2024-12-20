package com.sandra.car_rentals.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sandra.car_rentals.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByUsernameAndEmail(String username, String email);

    User findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);

    List<User> findByUsernameContainingIgnoreCase(String username);

    // Add this method
    Optional<User> findByEmail(String email);

     @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :username, '%'))")
    List<User> searchByUsername(String username);
}