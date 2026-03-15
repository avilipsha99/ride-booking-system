package com.ridebooking.driverservice.repository;

import com.ridebooking.driverservice.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findFirstByStatus(Driver.DriverStatus status);
    Optional<Driver> findByEmail(String email);
}