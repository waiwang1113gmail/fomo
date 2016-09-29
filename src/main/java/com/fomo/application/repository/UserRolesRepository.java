package com.fomo.application.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.fomo.application.entity.UserRole;

public interface UserRolesRepository extends CrudRepository<UserRole, Integer>{
	List<UserRole> findByUserId(String email);
}
