package com.servicesproject.project.Controllers;


import com.servicesproject.project.services.NotificationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationsController {
    @Autowired
    private NotificationServices notser ;
    @GetMapping("/unseen")
    public ResponseEntity<?> unseen(){

        return notser.get_notifications();
    }


}
