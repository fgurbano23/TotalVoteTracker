package com.cgts.services.controller;

import com.cgts.services.model.entity.UserAccount;
import com.cgts.services.model.service.UserAccountService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/users")
public class UserAccountRestController {

    @Autowired
    private UserAccountService userAccountService;

    //Get All Users
    @GetMapping("/accounts")
    public Object getUserAccounts() {
        List<UserAccount> resultData = userAccountService.getUserAccounts();
        Gson gson = new Gson();
        String json = gson.toJson(resultData, new TypeToken<List<UserAccount>>() {}.getType());
        return json;
    }
    //Get Specific User
    @GetMapping("/accounts/{userId}")
    public Object getUserAccount(@PathVariable String userId) {
        UserAccount userAccount = userAccountService.getUserAccount(userId);
        Gson gson = new Gson();
        String json = gson.toJson(userAccount, new TypeToken<UserAccount>() {}.getType());
        return json;
    }
    //Add new userAccount (Id Auto-Increase)
    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public UserAccount addUserAccount (@RequestBody UserAccount userAccount){
        userAccountService.saveUserAccount(userAccount);
        return userAccount;
    }
    //Modify Existing UserAccount
    @PutMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public UserAccount updateUserAccount(@RequestBody UserAccount userAccount){
        userAccountService.saveUserAccount(userAccount);
        return userAccount;
    }
    //Delete UserAccount
    @DeleteMapping("/accounts/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserAccount(@PathVariable String userId){

        userAccountService.deleteUserAccount(userId);
    }
}