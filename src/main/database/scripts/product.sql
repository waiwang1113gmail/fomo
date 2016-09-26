CREATE TABLE IF NOT EXISTS  `PRODUCT` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(200) NOT NULL,
  `Description` VARCHAR(2000),
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC));
  
CREATE TABLE IF NOT EXISTS `PRODUCT_COLUMN_METADATA`(
	`ID` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `Name` VARCHAR(32) NOT NULL UNIQUE,
    `Description` VARCHAR(2000),
    `DisplayName` VARCHAR(32),
    `Order` INT,
    `Type` VARCHAR(16)
);

CREATE TABLE IF NOT EXISTS `PRODUCT_SPECIFICATION`(
	`ProductID` INT NOT NULL, 
	`ColumnID` INT NOT NULL,
	`Value`  VARCHAR(2000),
	FOREIGN KEY (`ProductID`) REFERENCES `PRODUCT`(`ID`) ON DELETE CASCADE,
	FOREIGN KEY (`ColumnID`) REFERENCES `PRODUCT_COLUMN_METADATA`(`ID`) ON DELETE CASCADE
);