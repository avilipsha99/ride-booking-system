package com.ridebooking.rideservice.service;

import com.ridebooking.rideservice.dto.RideEvent;
import com.ridebooking.rideservice.dto.RideRequest;
import com.ridebooking.rideservice.kafka.RideEventProducer;
import com.ridebooking.rideservice.model.Ride;
import com.ridebooking.rideservice.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RideService {

    private final RideRepository rideRepository;
    private final RideEventProducer rideEventProducer;

    public Ride bookRide(String riderEmail, RideRequest request) {

        // Step 1 — Create and save ride to PostgreSQL
        Ride ride = Ride.builder()
                .riderEmail(riderEmail)
                .pickupLocation(request.getPickupLocation())
                .dropLocation(request.getDropLocation())
                .status(Ride.RideStatus.REQUESTED)
                .requestedAt(LocalDateTime.now())
                .build();

        Ride savedRide = rideRepository.save(ride);
        log.info("Ride saved to DB with id: {}", savedRide.getId());

        // Step 2 — Publish event to Kafka
        RideEvent event = new RideEvent(
                savedRide.getId(),
                savedRide.getRiderEmail(),
                savedRide.getPickupLocation(),
                savedRide.getDropLocation(),
                savedRide.getStatus().name()
        );

        rideEventProducer.publishRideRequested(event);

        // Step 3 — Return saved ride to controller
        return savedRide;
    }

    public List<Ride> getRidesByRider(String riderEmail) {
        return rideRepository.findByRiderEmail(riderEmail);
    }

    public Ride getRideById(Long rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found: " + rideId));
    }

    public Ride cancelRide(Long rideId, String riderEmail) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found: " + rideId));

        if (!ride.getRiderEmail().equals(riderEmail)) {
            throw new RuntimeException("Unauthorized — this is not your ride!");
        }

        if (ride.getStatus() != Ride.RideStatus.REQUESTED) {
            throw new RuntimeException("Cannot cancel — ride already " + ride.getStatus());
        }

        ride.setStatus(Ride.RideStatus.CANCELLED);
        return rideRepository.save(ride);
    }
}
