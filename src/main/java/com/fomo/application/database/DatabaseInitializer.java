package com.fomo.application.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {
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

	@Bean(name = "databaseTemplate")
	@Autowired
	public JdbcTemplate getDatabaseTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	private String getProperty(String propertyName) {
		if (System.getProperty(propertyName) == null) {
			LOG.error("Property missing: " + propertyName);
			System.exit(1);
		}
		return System.getProperty(propertyName);
	}

	@Autowired
	JdbcTemplate databaseTemplate;
	private boolean initialized = false;
	private String sqlScriptDirectory = System.getProperty(DatabaseConstants.DATABASE_SCRIPT_PATH_PROPERTY,
			DatabaseConstants.DATABASE_SCRIPT_PATH_DEFAULT);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (!initialized) {
			LOG.info("Initializing database.");
			initialized = true;
			Path scriptsPath = Paths.get(sqlScriptDirectory);
			if (!Files.exists(scriptsPath)) {
				LOG.error("Error: the directory that contains database scripts doesn's exit");
				LOG.error(scriptsPath.toAbsolutePath().toString());
				System.exit(1);
			}
			executeScriptsCreatingDatabase(scriptsPath);
		} else {
			LOG.warn("Database has been initialized");
		}
	}

	private void executeScriptsCreatingDatabase(Path scriptsPath) {
		try {
			if (Files.isDirectory(scriptsPath)) {
				Files.list(scriptsPath).filter(f -> {
					return f.toString().endsWith(DatabaseConstants.DATABASE_SUFFIX);
				}).forEach(f -> executeScriptsCreatingDatabase(f));
				;
			} else {
				String[] scripts = new String(Files.readAllBytes(scriptsPath))
						.split(DatabaseConstants.DATABASE_SCRIPT_SEPARATOR);
				ArrayList<String> scriptsWithoutEmpty=new ArrayList<>();
				Stream.of(scripts).filter(s -> s!=null&& s.trim().length()>1)
					.forEach(s ->scriptsWithoutEmpty.add(s));
				LOG.debug("Executing scripts: ");
				LOG.debug(scriptsWithoutEmpty.toString());
			
				databaseTemplate.batchUpdate(scriptsWithoutEmpty.toArray(new String[scriptsWithoutEmpty.size()]));
			}
		} catch (IOException e) {
			LOG.error("Failed to proecess script files: " + scriptsPath, e);
		}catch (Exception e) {
			LOG.error("error executing scripts: " + scriptsPath, e);
		}

	}

}
