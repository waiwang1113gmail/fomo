package com.fomo.application.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserManager {
	@Autowired
	JdbcTemplate databaseTemplate;
}
