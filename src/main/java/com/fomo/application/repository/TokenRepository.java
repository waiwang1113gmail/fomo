package com.fomo.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fomo.application.entity.Token;
 

public interface TokenRepository extends JpaRepository<Token, String>{

}
