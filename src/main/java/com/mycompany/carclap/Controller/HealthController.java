package com.mycompany.carclap.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {})
public class HealthController {

    @GetMapping("/health")
    public String health(){
        return "Up and Kicking";
    }
}
