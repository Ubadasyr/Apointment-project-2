package com.servicesproject.project.Model.Entity;

import com.servicesproject.project.DTO.userdto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class users implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String fullName;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }



    @Override
    public String getUsername() {
        return this.email;
    }

    public enum Role { ADMIN, STAFF, CUSTOMER }
    @Enumerated(EnumType.STRING)
    @Column(nullable=true)

    private Role role = Role.CUSTOMER;


    public  static users toEntity(userdto user){

        return users.builder().id(user.getId()).fullName(user.getFullName()).email(user.getEmail())
                .role(user.getRole()).build();

    }



}
