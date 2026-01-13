package com.servicesproject.project.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appoinments {
    public Appoinments(Long serviceId, Long staffId, int i, LocalDateTime bookStart, LocalDateTime bookEnd) {
    }

    public enum AppointmentStatus { PENDING, APPROVED, CANCELLED, FINISHED }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(optional=false)
    @JoinColumn(name="service_id")
    private services service;

    @ManyToOne(optional=false)
    @JoinColumn(name="staff_id")
    private users staff;

    @ManyToOne(optional=false)
    @JoinColumn(name="customer_id")
    private users customer;

    @Column(nullable=false)
    private LocalDateTime startTime;

    @Column(nullable=false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private AppointmentStatus status;





}
