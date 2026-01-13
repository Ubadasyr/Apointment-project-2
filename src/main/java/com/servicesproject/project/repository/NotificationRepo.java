package com.servicesproject.project.repository;

import com.servicesproject.project.Model.Entity.Notifications;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepo  extends JpaRepository<Notifications,Long > {

    List<Notifications> findByUser_Id(Long userId);

    List<Notifications> findByUser_IdAndSeenFalse(Long userId);
}
