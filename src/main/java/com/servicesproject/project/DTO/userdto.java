package com.servicesproject.project.DTO;

import com.servicesproject.project.Model.Entity.users;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor



public class userdto {

    private Long id;
    @NotBlank
    private String fullName;


    @NotBlank(message = "Email is required")
    @Email
    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters")
    @NotBlank(message = "Password is required")
    private String password;
    public enum Role { ADMIN, STAFF, CUSTOMER }
    private users.Role role;
    public  static  userdto  toDto(users user){

        return userdto.builder().id(user.getId()).fullName(user.getFullName()).email(user.getEmail())
                .password(user.getPassword()).role(user.getRole()).build();

    }






}
