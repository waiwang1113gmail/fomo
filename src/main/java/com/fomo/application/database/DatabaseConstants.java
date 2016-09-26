package com.fomo.application.database;
/**
 * 
 * @author Weige
 * Class that contains all constants
 */
class DatabaseConstants {
	public static final String DATABASE_URL_PROPERTY="fomo.database.url";
	public static final String DATABASE_DRIVER_PROPERTY="fomo.database.driver";
	public static final String DATABASE_USERNAME_PROPERTY="fomo.database.username";
	public static final String DATABASE_PASSWORD_PROPERTY="fomo.database.password";
	
	public static final String DATABASE_SCRIPT_PATH_PROPERTY="fomo.database.script.dir";
	public static final String DATABASE_SCRIPT_PATH_DEFAULT="src/main/database/scripts";
	public static final String DATABASE_SUFFIX = "sql";
	public static final String DATABASE_SCRIPT_SEPARATOR = ";";
}
