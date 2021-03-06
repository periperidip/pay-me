-- users table query: 

CREATE TABLE users(
	userID INT PRIMARY KEY,
	name VARCHAR(30) NOT NULL,
	address VARCHAR(50),
	phone_number VARCHAR(10) NOT NULL,
	email VARCHAR(255) UNIQUE, 
	aadhar_number VARCHAR(12) NOT NULL,
	password VARCHAR(128) NOT NULL,
	date_of_birth DATE NOT NULL,
	pincode INT,
	gender VARCHAR(10),
	pan_number VARCHAR(10) NOT NULL,
	CONSTRAINT email_constraint CHECK ( email LIKE '%_@_%._%'),
	CONSTRAINT phone_number_constraint CHECK ( phone_number LIKE '__________'),
	CONSTRAINT pincode_constraint CHECK (pincode between 100000 AND 999999)                                
);

-- accounts table query: 

CREATE TABLE accounts(
	userID INT,
	account_number VARCHAR(18) PRIMARY KEY,
	balance INT,
	pin VARCHAR(4),
	CONSTRAINT pin_constraint CHECK ( pin LIKE '____'),
	FOREIGN KEY(userID) REFERENCES users(userID) ON DELETE CASCADE
);

-- transaction table query:

CREATE TABLE transaction(
	transactionID VARCHAR(20) PRIMARY KEY,
	to_account_number VARCHAR(18),
	from_account_number VARCHAR(18),
	amount INT,
	timestamp TIMESTAMP,
	FOREIGN KEY(to_account_number) REFERENCES accounts(account_number) ON DELETE NO ACTION,
	FOREIGN KEY(from_account_number) REFERENCES accounts(account_number) ON DELETE NO ACTION
);

-- admin table query: 

CREATE TABLE admin(
	name VARCHAR(30) NOT NULL,
	id INT PRIMARY KEY,
	password VARCHAR(128)
);

-- admin query

INSERT INTO admin VALUES ("Saksham", 77001, "saksham"),
						 ("Deepanshu", 77002, "deepanshu"),
						 ("Shourya", 77003, "shourya"),
						 ("Palash", 77004, "palash"), 
						 ("Abhay", 77005, "abhay");  

-- report table query

CREATE TABLE report(
	report_number INT PRIMARY KEY,
	title VARCHAR(64) NOT NULL,
	userID INT,
	body VARCHAR(256),
	solved BOOLEAN,
	FOREIGN KEY(userID) REFERENCES users(userID) ON DELETE NO ACTION
);

-- Dummy Report query

INSERT INTO report VALUES (69001, "NOT WORKING", 90001, "NOT WORKING", FALSE);
INSERT INTO report VALUES (69002, "NOT WORKING", 90001, "NOT WORKING", FALSE);
INSERT INTO report VALUES (69003, "NOT WORKING", 90002, "NOT WORKING", FALSE);
INSERT INTO report VALUES (69004, "NOT WORKING", 90003, "NOT WORKING", FALSE);
INSERT INTO report VALUES (69005, "NOT WORKING", 90001, "NOT WORKING", TRUE);
INSERT INTO report VALUES (69006, "NOT WORKING", 90002, "NOT WORKING", FALSE);
INSERT INTO report VALUES (69007, "NOT WORKING", 90003, "NOT WORKING", FALSE);
INSERT INTO report VALUES (69008, "NOT WORKING", 90001, "NOT WORKING", TRUE);
INSERT INTO report VALUES (69009, "NOT WORKING", 90002, "NOT WORKING", FALSE);
INSERT INTO report VALUES (69010, "NOT WORKING", 90001, "NOT WORKING", FALSE);