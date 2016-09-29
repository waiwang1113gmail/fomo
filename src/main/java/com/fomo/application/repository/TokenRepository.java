package com.fomo.application.repository;

import org.springframework.data.repository.CrudRepository;

import com.fomo.application.entity.Token;
 

public interface TokenRepository extends CrudRepository<Token, String>{

}
