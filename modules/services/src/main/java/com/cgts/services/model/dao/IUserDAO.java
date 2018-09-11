package com.cgts.services.model.dao;

import com.cgts.services.model.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface IUserDAO extends CrudRepository<User, Double> { }
