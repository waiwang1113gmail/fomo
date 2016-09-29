package com.fomo.application.database;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer{
	private static Logger LOG = LoggerFactory.getLogger(DatabaseInitializer.class);

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(getProperty(DatabaseConstants.DATABASE_DRIVER_PROPERTY));
		dataSource.setUrl(getProperty(DatabaseConstants.DATABASE_URL_PROPERTY));
		dataSource.setUsername(getProperty(DatabaseConstants.DATABASE_USERNAME_PROPERTY));
		dataSource.setPassword(getProperty(DatabaseConstants.DATABASE_PASSWORD_PROPERTY));
		return dataSource;
	}

	private String getProperty(String propertyName) {
		if (System.getProperty(propertyName) == null) {
			LOG.error("Property missing: " + propertyName);
			System.exit(1);
		}
		return System.getProperty(propertyName);
	}

}
