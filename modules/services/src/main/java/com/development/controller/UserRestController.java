package com.development.controller;

import com.development.model.entity.User;
import com.development.model.service.IUserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*; //CrossOrigin, RestController, GetMapping PostMapping, ResponseStatus, PutMapping, DeleteMapping;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
public class UserRestController {

    @Autowired
    IUserService userService;

    //Get All Users
    @GetMapping("/user")
    public Object getUserAccounts() {
        List<User> resultData = userService.getUsers();
        Gson gson = new Gson();
        String json = gson.toJson(resultData, new TypeToken<List<User>>() {}.getType());
        return json;
    }
    //Get Specific User
    @GetMapping("/user/{userId}")
    public Object getUserAccount(@PathVariable Double userId) {
        User user = userService.getUser(userId);
        Gson gson = new Gson();
        String json = gson.toJson(user, new TypeToken<User>() {}.getType());
        return json;
    }
    //Add new userAccount (Id Auto-Increase)
    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUserAccount (@RequestBody User user){
        userService.saveUser(user);
        return user;
    }
    //Modify Existing UserAccount
    @PutMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User updateUserAccount(@RequestBody User user){
        userService.saveUser(user);
        return user;
    }
    //Delete UserAccount
    @DeleteMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserAccount(@PathVariable Integer userId){
        //Falta Determinar como sera el Delete de usuarios;
    }

}
