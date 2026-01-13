package com.servicesproject.project.Controllers;


import com.servicesproject.project.DTO.ServiceCreateRequest;
import com.servicesproject.project.DTO.UpdateServiceRequest;
import com.servicesproject.project.services.SERService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ser")
public class ServicesController {
    @Autowired
private SERService sers;
@PostMapping("create")
    public ResponseEntity<?> CreateService( @Valid @RequestBody ServiceCreateRequest req){
        return sers.createService(req);

    }
    @GetMapping("/get")
    public ResponseEntity<?> getServices(){
return sers.getServices();

    }
    @PostMapping  ("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody UpdateServiceRequest ser){
        return sers.update(id , ser);

    }

}
