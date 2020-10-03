CREATE TABLE table_user (
  id VARCHAR ( 40 ) PRIMARY KEY,
	username VARCHAR ( 40 ),
	realname VARCHAR ( 40 ),
	userpassword VARCHAR ( 40 ),
	sexual VARCHAR ( 8 )
);

CREATE TABLE table_employee (
	id VARCHAR ( 40 ) PRIMARY KEY,
	employeename VARCHAR ( 40 ),
	salary DOUBLE ( 7, 2 ),
	age INT ( 3 ),
	birthday date
);