package com.development.controller;

import com.development.model.service.UserStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
public class UserStatusController {

    @Autowired
    UserStatusService userStatusService;


    @GetMapping("/users/status/{userId}")

    public Object getUserStatus(@PathVariable Integer userId) {

        return userStatusService.getStatus(userId);
    }

    @GetMapping("/users/permission/{userId}")
    public Object getUserPermmissions(@PathVariable Integer userId){
        return userStatusService.getPermm(userId);
    }
}
