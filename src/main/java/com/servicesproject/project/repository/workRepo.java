package com.servicesproject.project.repository;

import com.servicesproject.project.Model.Entity.work_schedual;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface workRepo extends JpaRepository<work_schedual, Integer> {
    Optional<work_schedual> findByStaff_IdAndDayOfWeekAndClosedFalse(Long staffId, Integer dayOfWeek);
    boolean findByStaffIsNullAndDayOfWeekAndClosedFalse(Integer dayOfWeek);
}
