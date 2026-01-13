package com.servicesproject.project.repository;

import com.servicesproject.project.Model.Entity.services;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface serRepo extends JpaRepository<services,Long > {
   services findByName(String shaving);

}
