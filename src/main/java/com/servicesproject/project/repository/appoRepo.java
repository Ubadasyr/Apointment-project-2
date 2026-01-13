package com.servicesproject.project.repository;

import com.servicesproject.project.Model.Entity.Appoinments;
import com.servicesproject.project.Model.Entity.users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface appoRepo extends JpaRepository<Appoinments , Integer> {

    List<Appoinments> findByStaff_IdAndStartTimeBetweenAndStatusNotIn(
            Long staffId,
            LocalDateTime dayStart,
            LocalDateTime dayEnd,
            List<Appoinments.AppointmentStatus> excluded
    );
    List<Appoinments> findByStartTimeBetween(
            LocalDateTime from, LocalDateTime to
    );
    List<Appoinments> findByStartTimeBetweenAndStatus(
            LocalDateTime from, LocalDateTime to, Appoinments.AppointmentStatus status
    );
    List<Appoinments> findByCustomer_Id(Long customerId);
    List<Appoinments> findByStaff_Id(Long staffId);

}
