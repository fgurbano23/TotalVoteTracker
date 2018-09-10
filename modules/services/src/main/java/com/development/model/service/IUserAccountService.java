package com.development.model.service;

import com.development.model.entity.UserAccount;

import java.util.List;

public interface IUserAccountService {

    public List<UserAccount> getUserAccounts();

    public void saveUserAccount(UserAccount theUserAccount);

    public UserAccount getUserAccount(String theId);

    public void deleteUserAccount(String theId);

}