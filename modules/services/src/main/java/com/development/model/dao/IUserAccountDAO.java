package com.development.model.dao;


import com.development.model.entity.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface IUserAccountDAO extends CrudRepository<UserAccount, String> { }


