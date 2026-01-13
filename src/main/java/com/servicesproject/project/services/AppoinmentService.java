package com.servicesproject.project.services;


import com.servicesproject.project.Model.Entity.*;
import com.servicesproject.project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class AppoinmentService {

    @Autowired
    private appoRepo apprepo;
    @Autowired
    private workRepo wrepo;
    @Autowired
    private serRepo serrepo;
    @Autowired
    private userservice usersrv;
    @Autowired
    private UsersRepo usersrepo;
    @Autowired
    private serviceStaffRepo ssrepo;
    @Autowired
    private NotificationRepo notrepo;

@Autowired
    NotificationWebSocket nwservice;
    public static record AppoinmentDTO(
            LocalDateTime start,
            LocalDateTime end
    ){}

@Autowired
    NotificationServices notificationServices;


    public ResponseEntity<?> getAvailableAppoinment(Long serviceId, Long staffId, LocalDate date) {
        services srv = serrepo.findById(serviceId)
                  .orElseThrow(() -> new RuntimeException("Service not found"));
        users us = usersrepo.findById(staffId)
                .orElseThrow(() -> new RuntimeException("staff not found"));

        int dow = date.getDayOfWeek().getValue();

        if(!ssrepo.existsByService_IdAndStaff_IdAndActiveTrue(serviceId,staffId)){throw new RuntimeException("This staff does not provide this service");}
        work_schedual ws = wrepo.findByStaff_IdAndDayOfWeekAndClosedFalse(staffId, dow)
                .orElseThrow(() -> new RuntimeException("Staff is closed / no schedule for this day"));


        LocalTime dayStart = ws.getStartTime();// دوام الموظف
        LocalTime dayEnd = ws.getEndTime();


        var status = List.of(Appoinments.AppointmentStatus.CANCELLED,Appoinments.AppointmentStatus.FINISHED);
        List<Appoinments> booked = apprepo
                .findByStaff_IdAndStartTimeBetweenAndStatusNotIn(staffId,  dayStart.atDate(date), dayEnd.atDate(date), status);


        LocalDateTime workStart = date.atTime(dayStart);
        LocalDateTime workEnd   = date.atTime(dayEnd);

        int serMinutes = srv.getDurationMinutes();

        List<AppoinmentDTO> result = new ArrayList<>();

        for (LocalDateTime  s = workStart; !s.plusMinutes(serMinutes).isAfter(workEnd); s = s.plusMinutes(serMinutes)) {
            LocalDateTime  e1= s;
            LocalDateTime e = s.plusMinutes(serMinutes);

            boolean o = booked.stream().anyMatch(a ->

                    e1.isBefore(a.getEndTime()) && e.isAfter(a.getStartTime())
            );

            if (!o ) result.add(new AppoinmentDTO(e1, e));
        }

        return ResponseEntity.ok(result);
    }

public ResponseEntity<?> BOOK(Long serviceId,Long staffId ,LocalDateTime date){

    services srv = serrepo.findById(serviceId)
            .orElseThrow(() -> new RuntimeException("Service not found"));
    Long id = usersrv.getcurrentuserid();
    users customer=usersrepo.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
    users us = usersrepo.findById(staffId)
            .orElseThrow(() -> new RuntimeException("staff not found"));
if(!ssrepo.existsByService_IdAndStaff_IdAndActiveTrue(serviceId,staffId)){return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This staff does not provide this service ");}
    int dow = date.getDayOfWeek().getValue();

    work_schedual ws = wrepo.findByStaff_IdAndDayOfWeekAndClosedFalse(staffId, dow)
            .orElseThrow(() -> new RuntimeException("Staff is closed / no schedule for this day"));


    LocalTime dayStart = ws.getStartTime();// دوام الموظف
    LocalTime dayEnd = ws.getEndTime();


    var excluded = List.of(Appoinments.AppointmentStatus.CANCELLED,Appoinments.AppointmentStatus.FINISHED);
    List<Appoinments> booked = apprepo
            .findByStaff_IdAndStartTimeBetweenAndStatusNotIn(staffId,  dayStart.atDate(date.toLocalDate()), dayEnd.atDate(date.toLocalDate()), excluded);
    LocalDateTime bookStart = date;
    LocalDateTime bookEnd = bookStart.plusMinutes(srv.getDurationMinutes());
    if (bookStart.toLocalTime().isBefore(dayStart) ||
            bookEnd.toLocalTime().isAfter(dayEnd)) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Appointment outside working hours");
    }
  boolean o = booked.stream().anyMatch(a ->
          bookStart.isBefore(a.getEndTime()) && bookEnd.isAfter(a.getStartTime()));
if (o){return ResponseEntity.status(HttpStatus.CONFLICT).body("the date is already booked");}
    System.out.println('1');

    Appoinments app = Appoinments.builder()
            .service(srv)
            .staff(us)
            .customer(customer)// من auth أو param
            .startTime(bookStart)
            .endTime(bookEnd)
            .status(Appoinments.AppointmentStatus.PENDING)
            .build();

  Appoinments appoinments=  apprepo.save(app);


    notificationServices.create_notification(customer,"Your appointment is pending ");
    notificationServices.create_notification(us," appointment for you  is pending ");




    List<users> admins = usersrepo.findByRole(users.Role.ADMIN);

    for (users admin : admins) {

        notificationServices.create_notification(admin,"New appointment pending ");

    }




return ResponseEntity.status(HttpStatus.OK).body(Map.of("Appoinment is Pending " ,appoinments.getId()));
}






}
