package com.fomo.application.repository;

import org.springframework.data.repository.CrudRepository;

import com.fomo.application.entity.User;
 

public interface UserRepository extends CrudRepository<User, Integer>{
	User findByEmail(String login);
}
