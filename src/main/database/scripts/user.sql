
CREATE TABLE  IF NOT EXISTS USER(
	`password` VARCHAR(45),
	`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `email` VARCHAR(100) NOT NULL,
    `first_name` VARCHAR(100) NOT NULL,
    `last_name` VARCHAR(100) NOT NULL
);
CREATE TABLE IF NOT EXISTS `USER_ROLE` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (ID),
  UNIQUE KEY (role,user_id),
  KEY (user_id),
  CONSTRAINT FOREIGN KEY (user_id) REFERENCES USER (id) ON UPDATE CASCADE);
  
CREATE TABLE IF NOT EXISTS TOKEN (
	`series` varchar(64) not null PRIMARY KEY, 
    `value` varchar(64) not null, 
    `user_id` integer not null,
    `ip_address` varchar(64),
    `user_agent` varchar(64),
    `date` timestamp not null,
	CONSTRAINT FOREIGN KEY (user_id) REFERENCES USER (id) ON UPDATE CASCADE
); 