package com.ridebooking.driverservice.kafka;

import com.ridebooking.driverservice.dto.RideEvent;
import com.ridebooking.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RideEventConsumer {

    private final DriverService driverService;

    @KafkaListener(
            topics = "ride.requested",
            groupId = "driver-service-group"
    )
    public void consumeRideRequest(RideEvent event) {
        log.info("Received ride request from Kafka: rideId={}, rider={}",
                event.getRideId(), event.getRiderEmail());
        driverService.assignDriver(event);
    }
}