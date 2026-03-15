package com.ridebooking.driverservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "drivers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String name;

    private String phone;

    private String vehicleNumber;

    @Enumerated(EnumType.STRING)
    private DriverStatus status;

    public enum DriverStatus {
        AVAILABLE,
        ON_RIDE,
        OFFLINE
    }
}