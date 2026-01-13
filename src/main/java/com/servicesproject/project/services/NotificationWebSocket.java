package com.servicesproject.project.services;


import com.servicesproject.project.Model.Entity.Notifications;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationWebSocket {

    private final SimpMessagingTemplate ms;

    public NotificationWebSocket(SimpMessagingTemplate ms) {
        this.ms = ms;
    }

    public void notificationSend(Long id , Notifications N){

        ms.convertAndSend("/notify/private."+id,N.getMessage());
    }


}
