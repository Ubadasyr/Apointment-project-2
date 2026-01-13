package com.servicesproject.project.repository;

import com.servicesproject.project.Model.Entity.users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepo extends JpaRepository<users,Long > {
boolean existsByEmail(String email);

    List<users> findByRole(users.Role role);
    //Optional<users> findByEmail(String email);
    users findByEmail(String email);
}
