package com.ridebooking.driverservice.service;

import com.ridebooking.driverservice.dto.RideEvent;
import com.ridebooking.driverservice.model.Driver;
import com.ridebooking.driverservice.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverService {

    private final DriverRepository driverRepository;

    public void assignDriver(RideEvent event) {
        log.info("Finding available driver for rideId: {}", event.getRideId());

        Driver driver = driverRepository
                .findFirstByStatus(Driver.DriverStatus.AVAILABLE)
                .orElseThrow(() -> new RuntimeException("No available drivers!"));

        driver.setStatus(Driver.DriverStatus.ON_RIDE);
        driverRepository.save(driver);

        log.info("Driver {} assigned to rideId: {}",
                driver.getEmail(), event.getRideId());
    }

    public Driver registerDriver(Driver driver) {
        driver.setStatus(Driver.DriverStatus.AVAILABLE);
        return driverRepository.save(driver);
    }
}