package com.ridebooking.rideservice.controller;

import com.ridebooking.rideservice.dto.RideRequest;
import com.ridebooking.rideservice.model.Ride;
import com.ridebooking.rideservice.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @PostMapping("/book")
    public ResponseEntity<Ride> bookRide(
            @RequestHeader("X-Rider-Email") String riderEmail,
            @RequestBody RideRequest request) {
        return ResponseEntity.ok(rideService.bookRide(riderEmail, request));
    }

    @GetMapping("/my-rides")
    public ResponseEntity<List<Ride>> getMyRides(
            @RequestHeader("X-Rider-Email") String riderEmail) {
        return ResponseEntity.ok(rideService.getRidesByRider(riderEmail));
    }

    @GetMapping("/{rideId}")
    public ResponseEntity<Ride> getRideById(@PathVariable Long rideId) {
        return ResponseEntity.ok(rideService.getRideById(rideId));
    }

    @PutMapping("/{rideId}/cancel")
    public ResponseEntity<Ride> cancelRide(
            @PathVariable Long rideId,
            @RequestHeader("X-Rider-Email") String riderEmail) {
        return ResponseEntity.ok(rideService.cancelRide(rideId, riderEmail));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Ride Service is UP and running!");
    }
}
