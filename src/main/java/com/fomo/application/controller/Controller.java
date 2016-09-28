package com.fomo.application.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fomo.application.entity.User;
import com.fomo.application.repository.UserRepository;
import com.fomo.application.security.SecurityUtils;

@RestController
public class Controller {
	
	@Autowired
	private UserRepository userRepo;
	
	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@RequestMapping("/resource")
	public Map<String, Object> home() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello World");
		return model;
	}

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@RequestMapping("/security/account")
	User getUserAccount() {
		User user = userRepo.findByLogin(SecurityUtils.getCurrentLogin());
		return user;
	}
	
}
