package com.servicesproject.project.services;

import com.servicesproject.project.DTO.LoginRequest;
import com.servicesproject.project.DTO.TokenResponse;
import com.servicesproject.project.DTO.userdto;
import com.servicesproject.project.Model.Entity.Appoinments;
import com.servicesproject.project.Model.Entity.Notifications;
import com.servicesproject.project.Model.Entity.users;
import com.servicesproject.project.Security.TokenUtil;
import com.servicesproject.project.repository.NotificationRepo;
import com.servicesproject.project.repository.UsersRepo;
import com.servicesproject.project.repository.appoRepo;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.autoconfigure.JacksonProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.security.Security;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class userservice implements UserDetailsService {
    @Autowired
    private UsersRepo usrepo;

    @Autowired
    private UsersRepo userrepo;
    @Autowired
    private appoRepo apprepo;
    @Autowired
    private NotificationRepo notrepo;





private final PasswordEncoder passenc;
    public userservice(
                       PasswordEncoder passwordEncoder) {

        this.passenc = passwordEncoder;
    }
    public ResponseEntity<Object> register(userdto user ){

        if (usrepo.existsByEmail(user.getEmail())) {
            System.out.println(user.getEmail());
            return  ResponseEntity.badRequest().body("Email is already exists");
        }

        users usernew = new users();
        usernew.setFullName(user.getFullName());
        usernew.setEmail(user.getEmail());
        usernew.setPassword(
               passenc.encode(user.getPassword())
        );

         usrepo.save(usernew);
        return new ResponseEntity<>("Registered", HttpStatus.OK);
    }



    public ResponseEntity<?> CANCEL(Integer Appid,Long id ){
        users u = userrepo.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
        Appoinments A = apprepo.findById(Appid).orElseThrow(() -> new RuntimeException("Appointment not found"));

        if(A.getStatus().equals(Appoinments.AppointmentStatus.CANCELLED)){return ResponseEntity.status(HttpStatus.FORBIDDEN).body("this appointment is already  canceled");}
        if (!A.getCustomer().equals(u) ){return ResponseEntity.status(HttpStatus.FORBIDDEN).body("this appointment not for you3.");}
        A.setStatus(Appoinments.AppointmentStatus.CANCELLED);
        apprepo.save(A);
        List<users> admins = userrepo.findByRole(users.Role.ADMIN);

        for (users admin : admins) {
            notrepo.save(
                    Notifications.builder()
                            .user(admin)
                            .message("customer cancel his appointment "+A.getId()+A.getCustomer().getFullName()+A.getStaff().getFullName())
                            .created_at(LocalDateTime.now())
                            .build()
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body("Appointment is Cancelled");
    }






    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        users u = userrepo.findByEmail(username);
       if(u == null){
           throw new RuntimeException("user not found ");

       }

       return  u;
    }


    public record AppointmenteDTO(
            Integer id,
            Long serviceId,
            String serviceName,
            Long staffId,
            String staffName,
            Long customerId,
            String customerName,
            Appoinments.AppointmentStatus status,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {}

    public ResponseEntity<?> getAppointmentsByCustomer(Long userId) {
        var list = apprepo.findByCustomer_Id(userId);

        var app = list.stream().map(a -> new AppointmenteDTO(
                a.getId(),
                a.getService().getId(),
                a.getService().getName(),
                a.getStaff().getId(),
                a.getStaff().getFullName(),
                a.getCustomer().getId(),
                a.getCustomer().getFullName(),
                a.getStatus(),
                a.getStartTime(),
                a.getEndTime()
        )).toList();

        return ResponseEntity.status(HttpStatus.OK).body(app);
    }


    public ResponseEntity<?> getAppointmentsByStaff(Long staffId) {
        var list = apprepo.findByStaff_Id(staffId);
        var app = list.stream().map(a -> new AppointmenteDTO(
                a.getId(),
                a.getService().getId(),
                a.getService().getName(),
                a.getStaff().getId(),
                a.getStaff().getFullName(),
                a.getCustomer().getId(),
                a.getCustomer().getFullName(),
                a.getStatus(),
                a.getStartTime(),
                a.getEndTime()
        )).toList();

        return ResponseEntity.status(HttpStatus.OK).body(app);
    }



public Long getcurrentuserid(){
    users user =(users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
}


}
