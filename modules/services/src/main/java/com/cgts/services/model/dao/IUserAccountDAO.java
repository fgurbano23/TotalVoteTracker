package com.cgts.services.model.dao;


import com.cgts.services.model.entity.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface IUserAccountDAO extends CrudRepository<UserAccount, String> { }


