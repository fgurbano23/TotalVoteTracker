package com.cgts.services.model.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IUserAccount extends JpaRepository<UserAccount,Long> {
}
