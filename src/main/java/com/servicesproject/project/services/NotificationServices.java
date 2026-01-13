package com.servicesproject.project.services;

import com.servicesproject.project.Model.Entity.Notifications;
import com.servicesproject.project.Model.Entity.users;
import com.servicesproject.project.repository.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class NotificationServices {
    @Autowired
    private NotificationRepo notrepo;
    @Autowired
    NotificationWebSocket nwservice;
    //مؤقت
@Autowired
    userservice userser;
    public ResponseEntity<?> get_notifications(){
        Long id =userser.getcurrentuserid();
         notrepo.findByUser_IdAndSeenFalse(id);
     return ResponseEntity.status(HttpStatus.OK).body(Map.of(
             "MESAAGE:",notrepo.findByUser_IdAndSeenFalse(id).stream().map(a-> a.getMessage()),notrepo.findByUser_IdAndSeenFalse(id).stream().map(a-> a.getMessage()),""

     ));
    }

    public void create_notification(users user , String Message){
        Notifications ns = notrepo.save(
                Notifications.builder()
                        .user(user) // staff
                        .message(Message)
                        .created_at(LocalDateTime.now())
                        .build()
        );

        nwservice.notificationSend(user.getId(),ns);
    }



}
