package com.nxthing.taxiservice.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Driver_requests")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DriverRequest {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name="driver_req_generator", sequenceName = "driver_req_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "driver_req_generator")
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "license", unique = true)
    private String license;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DriverRequestStatus status;
}
