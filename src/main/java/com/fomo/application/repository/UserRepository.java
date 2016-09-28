package com.fomo.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fomo.application.entity.User;
 

public interface UserRepository extends JpaRepository<User, Integer>{
	User findByLogin(String login);
}
