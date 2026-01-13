package com.servicesproject.project.Controllers;

import com.servicesproject.project.Model.Entity.Appoinments;
import com.servicesproject.project.Model.Entity.services;
import com.servicesproject.project.Model.Entity.users;
import com.servicesproject.project.repository.UsersRepo;
import com.servicesproject.project.repository.appoRepo;
import com.servicesproject.project.services.AdminServices;
import com.servicesproject.project.services.AppoinmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/Admin")
public class AdminController {
    @Autowired
    private AdminServices adminser;
    @GetMapping("/get/appointments")

    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<?> getAdminAppointments(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Appoinments.AppointmentStatus status

    ) {
        return ResponseEntity.ok(
                adminser.get_appointment(date, status)
        );
    }

@GetMapping("/approve")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<?> approve(@RequestParam Integer Appid  ){
        return  adminser.Accept(Appid);
}

    @GetMapping("/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> reject(@RequestParam Integer Appid  ){
        return  adminser.Reject(Appid);
    }
    @PostMapping("/update_status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update_status(@RequestParam Integer Appid  ,@RequestParam Appoinments.AppointmentStatus STATUS){
        return  adminser.updateStatus(Appid,STATUS);
    }

}
