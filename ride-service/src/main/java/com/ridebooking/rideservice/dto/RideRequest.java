package com.ridebooking.rideservice.dto;

import lombok.Data;

@Data
public class RideRequest {
    private String pickupLocation;
    private String dropLocation;
}