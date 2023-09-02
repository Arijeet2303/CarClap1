package com.mycompany.carclap.Controller;

import com.mycompany.carclap.Repository.VendorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {})
public class AdminController {

    @Autowired
    VendorRepo repo;

    @RequestMapping("/showAll" )
    public List show(Model model) {
        return repo.findAll();
    }
}
