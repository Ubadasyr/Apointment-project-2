package com.servicesproject.project.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class services {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private Integer durationMinutes;

    @Column(nullable=false)
    private Double price;

}
