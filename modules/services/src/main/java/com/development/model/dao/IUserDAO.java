package com.development.model.dao;

import com.development.model.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface IUserDAO extends CrudRepository<User, Double> { }
