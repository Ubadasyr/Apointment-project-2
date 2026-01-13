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
public class Notifications {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
@ManyToOne(optional = false)
@JoinColumn(name ="user_id")
private users user ;
    @Column(nullable = false)
private String message;
@Column(nullable = false)
private boolean seen=false;
@Column(nullable = false)
private LocalDateTime created_at;






}
