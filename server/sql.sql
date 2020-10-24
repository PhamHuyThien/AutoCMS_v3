CREATE DATABASE fplautocms_analysis;

USE fplautocms_analysis;

CREATE TABLE user(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(100) NOT NULL, 
	ip VARCHAR(20),
	city VARCHAR(50),
	region VARCHAR(50),
	country VARCHAR(50),
	timezone VARCHAR(50),
	time INT
);