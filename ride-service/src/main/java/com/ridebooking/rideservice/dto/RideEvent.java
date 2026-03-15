package com.ridebooking.rideservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideEvent {
    private Long rideId;
    private String riderEmail;
    private String pickupLocation;
    private String dropLocation;
    private String status;
}