package com.servicesproject.project.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"service_id", "staff_id"}))
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class srvice_staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_id")
    private services service;

    @ManyToOne(optional = false)
    @JoinColumn(name = "staff_id")
    private users staff;

    @Column(nullable = false)
    private boolean active = true;



}
