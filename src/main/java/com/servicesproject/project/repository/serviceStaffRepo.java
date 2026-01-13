package com.servicesproject.project.repository;

import com.servicesproject.project.Model.Entity.srvice_staff;
import com.servicesproject.project.Model.Entity.users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface serviceStaffRepo extends JpaRepository<srvice_staff,Long > {
    List<srvice_staff> findByService_Id(Long serviceId);
    boolean existsByService_IdAndStaff_IdAndActiveTrue(
            Long serviceId,
            Long staffId
    );
    Optional<srvice_staff> findByService_IdAndStaff_Id(Long serviceId, Long staffId);
    List<srvice_staff> findByService_IdAndActiveTrue(Long serviceId);
}
