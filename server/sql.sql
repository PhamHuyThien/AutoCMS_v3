
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


CREATE TABLE course(
	id VARCHAR(100) NOT NULL PRIMARY KEY,
	total_quiz INT,
	safety INT, 
	time_update INT, 
	time INT
);
