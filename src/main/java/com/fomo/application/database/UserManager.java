package com.fomo.application.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.fomo.application.entity.User;

@Component
public class UserManager {
	@Autowired
	JdbcTemplate databaseTemplate;
	 
}
