package com.ridebooking.rideservice.kafka;

import com.ridebooking.rideservice.dto.RideEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RideEventProducer {

    private static final String TOPIC = "ride.requested";

    private final KafkaTemplate<String, RideEvent> kafkaTemplate;

    public void publishRideRequested(RideEvent event) {
        log.info("Publishing ride event to Kafka: rideId={}, rider={}",
                event.getRideId(), event.getRiderEmail());
        kafkaTemplate.send(TOPIC, event.getRideId().toString(), event);
        log.info("Ride event published successfully to topic: {}", TOPIC);
    }
}
