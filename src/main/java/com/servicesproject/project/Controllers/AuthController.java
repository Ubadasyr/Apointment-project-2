package com.servicesproject.project.Controllers;


import com.servicesproject.project.DTO.LoginRequest;
import com.servicesproject.project.DTO.TokenResponse;
import com.servicesproject.project.DTO.userdto;
import com.servicesproject.project.Model.Entity.users;
import com.servicesproject.project.Security.TokenUtil;
import com.servicesproject.project.services.userservice;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/Auth")
public class AuthController {
    @Autowired
    private userservice usersrv;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody userdto user ){
        return usersrv.register(user);
    }
    @PostMapping("/Login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest user ){
        final Authentication auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        UserDetails userd=usersrv.loadUserByUsername(user.getEmail());

        String token =tokenUtil.generateToken(userd);
        users users=(users) userd;
        TokenResponse res =TokenResponse.builder().Token(token).message("login done").ID(users.getId()).username(users.getFullName()).build();

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }



    @GetMapping("/cancel")
    public ResponseEntity<?> cancel(@RequestParam Integer Appid  ){
        Long id =usersrv.getcurrentuserid();

        return  usersrv.CANCEL(Appid,id);
    }


    @GetMapping("/app/by-user")
    public ResponseEntity<?> byUser(@RequestParam Long userId) {
        return usersrv.getAppointmentsByCustomer(userId);
    }


    @GetMapping("/app/staff")
    public ResponseEntity<?> byStaff(@RequestParam Long staffId) {
        return usersrv.getAppointmentsByStaff(staffId);
    }

    @GetMapping("/myappoinments")
    public ResponseEntity<?> myappointments(){
      Long id =usersrv.getcurrentuserid();

        return usersrv.getAppointmentsByCustomer(id);
    }


}
