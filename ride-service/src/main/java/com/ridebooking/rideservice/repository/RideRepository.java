package com.ridebooking.rideservice.repository;

import com.ridebooking.rideservice.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByRiderEmail(String riderEmail);
    List<Ride> findByStatus(Ride.RideStatus status);
}