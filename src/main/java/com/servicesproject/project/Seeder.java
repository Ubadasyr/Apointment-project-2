package com.servicesproject.project;

import com.servicesproject.project.Model.Entity.services;
import com.servicesproject.project.Model.Entity.srvice_staff;
import com.servicesproject.project.Model.Entity.users;
import com.servicesproject.project.Model.Entity.work_schedual;
import com.servicesproject.project.repository.UsersRepo;
import com.servicesproject.project.repository.serRepo;
import com.servicesproject.project.repository.serviceStaffRepo;
import com.servicesproject.project.repository.workRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
@Component
public class Seeder implements CommandLineRunner {
@Autowired
UsersRepo usersRepo;
@Autowired
    serRepo serRepo;
@Autowired
workRepo  workRepo;
@Autowired
serviceStaffRepo serviceStaffRepo;

private final PasswordEncoder passenc;
    public Seeder(
            PasswordEncoder passwordEncoder) {

        this.passenc = passwordEncoder;
    }




    @Override
    public void run(String... args) throws Exception {
seedusers();
seedService();
workseeder();
seedServiceStaff();
    }

    public void seedusers(){
        if(usersRepo.count()>0) return;

        users admin = new users(null,"Admin","admin@admin.com", passenc.encode("123456789"), users.Role.ADMIN );
        usersRepo.save(admin);
        for (long i =2 ;i<5 ; i++){
            String name = "staff"+i;
            String email = name+"@mail.com";
            users staff = new users(null,name,email, passenc.encode("123456789"), users.Role.STAFF );
            usersRepo.save(staff);
seedStaff(staff.getId());

        }



    }

    public void seedService(){

        if(serRepo.count()>0)return;
        services s1 = new services(null,"web development",30,5000.0);
        services s2 = new services(null,"mobile development",45,5000.0);
        services s3 = new services(null," game development",50,5000.0);
serRepo.saveAll(List.of(s1,s2,s3));

    }

   public void workseeder(){
       for (int i = 1; i <= 7; i++) {
           boolean closed = (i == 6 || i == 7); // Sat/Sun closed
           workRepo.save(new work_schedual(
                   null,
                   i,
                   closed ? LocalTime.of(0,0) : LocalTime.of(9,0),
                   closed ? LocalTime.of(0,0) : LocalTime.of(17,0),
                   closed,
                   null // staff=null => company
           ));
       }

   }
    private void seedStaff(Long staffId) {
        users staff = usersRepo.findById(staffId).orElseThrow();

        for (int dow = 1; dow <= 5; dow++) {
            workRepo.save(new work_schedual(null, dow, LocalTime.of(9,0), LocalTime.of(17,0), false, staff));
        }
    }

    private void seedServiceStaff() {
        if(serviceStaffRepo.count()>0)return;
        // مثال: جيبهم بالـ email و name (أضمن من id)
        users staff1 =  usersRepo.findByEmail("staff3@mail.com");
        users staff2 = usersRepo.findByEmail("staff2@mail.com");
        users staff3 = usersRepo.findByEmail("staff4@mail.com");

        services s1 =  serRepo.findByName("web development");
        services s2 = serRepo.findByName("mobile development");
        services s3 = serRepo.findByName(" game development");

        linkIfNotExists(s1, staff1);
        linkIfNotExists(s2, staff2);
        linkIfNotExists(s3, staff3);
    }

    private void linkIfNotExists(services service, users staff) {
        boolean exists = serviceStaffRepo.existsByService_IdAndStaff_IdAndActiveTrue(
                service.getId(), staff.getId()
        );
        if (!exists) {
            serviceStaffRepo.save(new srvice_staff(null, service, staff, true));
        }
    }


}
