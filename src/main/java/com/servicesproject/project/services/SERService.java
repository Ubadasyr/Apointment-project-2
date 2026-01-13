package com.servicesproject.project.services;

import com.servicesproject.project.DTO.ServiceCreateRequest;
import com.servicesproject.project.DTO.UpdateServiceRequest;
import com.servicesproject.project.Model.Entity.services;
import com.servicesproject.project.Model.Entity.srvice_staff;
import com.servicesproject.project.Model.Entity.users;
import com.servicesproject.project.repository.UsersRepo;
import com.servicesproject.project.repository.serRepo;
import com.servicesproject.project.repository.serviceStaffRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service

public class SERService {
    @Autowired
   private serRepo serrepo;
    @Autowired
    private UsersRepo userRepo;
    @Autowired
    private serviceStaffRepo serviceStaffRepo;

    public ResponseEntity<?> createService(ServiceCreateRequest req) {

        services s = new services();
        s.setName(req.getName());
        s.setDurationMinutes(req.getDurationMinutes());
        s.setPrice(req.getPrice());

        services saved = serrepo.save(s);
        users   staff= new users();

        for (Long staffId : req.getStaffIds()) {
            Optional<users> staffOpt = userRepo.findById(staffId);

            if (staffOpt.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Staff not found: " + staffId));
            }

        staff = staffOpt.get();

            if (staff.getRole() != users.Role.STAFF) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "User " + staffId + " is not STAFF"));
            }
            srvice_staff link = new srvice_staff();
            link.setService(saved);

            link.setActive(true);
            link.setStaff(staff);
            serviceStaffRepo.save(link);
            }




        return ResponseEntity.ok("Service created with staff");
        }

    public record StaffDTO(
            Long id,
            String fullName
    ) {}
    public record ServiceStaffDTO(
            Long serviceId,
            String serviceName,
            Integer durationMinutes,
            Double price,
            List<StaffDTO> staff
    ) {}
    public ResponseEntity<?> getServices(){
        List<services> allServices = serrepo.findAll();
        List<ServiceStaffDTO> result = new ArrayList<>();

        for (services s : allServices) {

            List<srvice_staff> links =
                    serviceStaffRepo.findByService_IdAndActiveTrue(s.getId());

            List<StaffDTO> staffList = links.stream()
                    .map(link -> new StaffDTO(
                            link.getStaff().getId(),
                            link.getStaff().getFullName()
                    ))
                    .toList();

            result.add(new ServiceStaffDTO(
                    s.getId(),
                    s.getName(),
                    s.getDurationMinutes(),
                    s.getPrice(),
                    staffList
            ));
        }



return ResponseEntity.ok(result);

    }
    public ResponseEntity<?> update(Long serid , UpdateServiceRequest req){

    services s = serrepo.findById(serid)
            .orElseThrow(() -> new RuntimeException("Service not found"));

    s.setName(req.getName());
    s.setDurationMinutes(req.getDurationMinutes());
    s.setPrice(req.getPrice());
    serrepo.save(s);



    List<srvice_staff> current = serviceStaffRepo.findByService_Id(serid);

    Set<Long> newIds = new HashSet<>(req.getStaffIds());
    Set<Long> oldIds = current.stream().map(x -> x.getStaff().getId()).collect(Collectors.toSet());

    for (srvice_staff link : current) {
        Long staffId = link.getStaff().getId();
        if (!newIds.contains(staffId)) {
            link.setActive(false);
            serviceStaffRepo.save(link);
        } else {
            link.setActive(true);
            serviceStaffRepo.save(link);
        }
    }


    for (Long staffId : newIds) {
        if (!oldIds.contains(staffId)) {
            users staff = userRepo.findById(staffId)
                    .orElseThrow(() -> new RuntimeException("Staff not found: " + staffId));

            if (staff.getRole() != users.Role.STAFF)
                return ResponseEntity.badRequest().body("User " + staffId + " is not STAFF");

            srvice_staff link = serviceStaffRepo.findByService_IdAndStaff_Id(serid, staffId)
                    .orElseGet(srvice_staff::new);

            link.setService(s);
            link.setStaff(staff);
            link.setActive(true);
            serviceStaffRepo.save(link);
        }
    }

    return ResponseEntity.ok("Service updated");

}


    }




