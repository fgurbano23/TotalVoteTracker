package com.cgts.services.model.service;

import com.cgts.services.model.dao.IUserAccountDAO;
import com.cgts.services.model.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserAccountService implements IUserAccountService {

    @Autowired
    private IUserAccountDAO userAccountDAO;

    @Override
    @Transactional(readOnly = true)
    public List<UserAccount> getUserAccounts() {
        return (List<UserAccount>) userAccountDAO.findAll();
    }

    @Override
    @Transactional
    public void saveUserAccount(UserAccount theUserAccount){
        userAccountDAO.save(theUserAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public UserAccount getUserAccount(String theId){
        return userAccountDAO.findById(theId).get();
    }

    @Override
    @Transactional
    public void deleteUserAccount(String theId){
        userAccountDAO.deleteById(theId);
    }
}