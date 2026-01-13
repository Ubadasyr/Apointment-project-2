package com.servicesproject.project.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Table


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class work_schedual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    @Column(nullable = false)
    private Integer dayOfWeek;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private boolean closed = false;

    @ManyToOne(optional = true)
    @JoinColumn(name = "staff_id")
    private users staff;



}
