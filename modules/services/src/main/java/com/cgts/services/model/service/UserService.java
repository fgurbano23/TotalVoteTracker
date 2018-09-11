package com.cgts.services.model.service;

import com.cgts.services.model.dao.IUserDAO;
import com.cgts.services.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserDAO userDAO;

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return (List<User>) userDAO.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User theUserAccount){
        userDAO.save(theUserAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Double theId){

        return userDAO.findById(theId).get();
    }

    @Override
    @Transactional
    public void deleteUser(Double theId){
        userDAO.deleteById(theId);
    }


}