package com.servicesproject.project.services;

import com.servicesproject.project.Model.Entity.Appoinments;
import com.servicesproject.project.Model.Entity.Notifications;
import com.servicesproject.project.Model.Entity.users;
import com.servicesproject.project.repository.NotificationRepo;
import com.servicesproject.project.repository.UsersRepo;
import com.servicesproject.project.repository.appoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SQLOutputImpl;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminServices {

    @Autowired
    private UsersRepo userrepo;
    @Autowired
    private appoRepo apprepo;
    @Autowired
    private NotificationRepo notrepo;
    @Autowired
    NotificationServices notificationServices;
    public static record AppoinmentDTO(
            Integer ID,
            LocalDateTime start,
            LocalDateTime end,
            Long Staff_id,
            String Staff_name,
            Long customer_id,
            String customer_name
            ,String service_title,
            Appoinments.AppointmentStatus status
    ){

    }

    public List<AppoinmentDTO> get_appointment(
            LocalDate date,
            Appoinments.AppointmentStatus status
    ){
        LocalDateTime from = date.atStartOfDay();
        LocalDateTime to   = date.plusDays(1).atStartOfDay();



        if (status != null) {
            System.out.println("obada");
           return apprepo.findByStartTimeBetweenAndStatus(from, to, status).stream().map(a -> new AppoinmentDTO(a.getId(),a.getStartTime(), a.getEndTime(), a.getStaff().getId(), a.getStaff().getFullName(), a.getCustomer().getId(), a.getCustomer().getFullName(), a.getService().getName(), a.getStatus())).toList();

        }


        return apprepo.findByStartTimeBetween(from, to).stream().map(a->new AppoinmentDTO(a.getId(),a.getStartTime(),a.getEndTime(),a.getStaff().getId(),a.getStaff().getFullName(),a.getCustomer().getId(),a.getCustomer().getFullName(),a.getService().getName(),a.getStatus())).toList();



    }

    public ResponseEntity<?> Accept(Integer Appid ){
        Appoinments A = apprepo.findById(Appid).orElseThrow(() -> new RuntimeException("Appointment not found"));
        A.setStatus(Appoinments.AppointmentStatus.APPROVED);
        apprepo.save(A);
        users customer =A.getCustomer();
        notificationServices.create_notification(customer,"Your appointment is "+A.getStatus());
        return ResponseEntity.status(HttpStatus.OK).body("Appointment is approved");
    }
    public ResponseEntity<?> Reject(Integer Appid  ){

        Appoinments A = apprepo.findById(Appid).orElseThrow(() -> new RuntimeException("Appointment not found"));
        A.setStatus(Appoinments.AppointmentStatus.CANCELLED);
        apprepo.save(A);
        users customer =A.getCustomer();
        notificationServices.create_notification(customer,"Your appointment is "+A.getStatus());
        return ResponseEntity.status(HttpStatus.OK).body("Appointment is Cancelled");
    }

   public  ResponseEntity<?> updateStatus(Integer id, Appoinments.AppointmentStatus status) {

        Appoinments ap = apprepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        ap.setStatus(status);
       apprepo.save(ap);
users customer =ap.getCustomer();
       notificationServices.create_notification(customer,"Your appointment is "+ap.getStatus());
        return ResponseEntity.ok("Status updated to " + status);
    }




}
