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
	
	public User findByLogin(String username){
		User u=databaseTemplate.queryForObject("SELECT Firstname,LastName,Email FROM USER,USER_DETAIL,USER_ROLES WHERE Email = ?",new Object[]{username}, new RowMapper<User>(){
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User u=new User();
				u.setFirstName(rs.getString("Firstname"));
				u.setLastName(rs.getString("LastName"));
				u.setEmail(rs.getString("Email"));
				return u;
			}
			
		} );
		return u;
	}
}
