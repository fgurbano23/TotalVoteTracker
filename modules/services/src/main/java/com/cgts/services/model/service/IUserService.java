package com.cgts.services.model.service;

import com.cgts.services.model.entity.User;

import java.util.List;

public interface IUserService {

    public List<User> getUsers();

    public void saveUser(User theUserAccount);

    public User getUser(Double theId);

    public void deleteUser(Double theId);

}