package com.cgts.services.model.service;

import com.cgts.services.model.entity.UserAccount;

import java.util.List;

public interface IUserAccountService {

    public List<UserAccount> getUserAccounts();

    public void saveUserAccount(UserAccount theUserAccount);

    public UserAccount getUserAccount(String theId);

    public void deleteUserAccount(String theId);

}