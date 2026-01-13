package com.servicesproject.project.Controllers;


import com.servicesproject.project.repository.NotificationRepo;
import com.servicesproject.project.services.AppoinmentService;
import com.servicesproject.project.services.userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/Appoinment")
public class AppoinmentsController {
    @Autowired
    private AppoinmentService appser;
    @Autowired
    private NotificationRepo notrepo;
@GetMapping("/get/Available")
public ResponseEntity<?> get_Available_appointment(@RequestParam(name="ser_id") Long ser_id ,
                                                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date,
                                                                                       @RequestParam(required = false) Long Staff_id){


    return
            appser.getAvailableAppoinment(ser_id, Staff_id, date)
   ;
}



/// app/Book
 @GetMapping("/Book")

    public ResponseEntity<?> Book(@RequestParam(name="ser_id") Long ser_id ,
                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime date,
                                             @RequestParam(required = false) Long Staff_id){


        return appser.BOOK(ser_id, Staff_id, date);
    }


}
