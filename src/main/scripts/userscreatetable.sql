--Ethan Chen
--February 15, 2023
--This sql file will insert 6 users; 3 students and 3 faculty according to their information.

DROP TABLE IF EXISTS users, faculty, students;
DROP EXTENSION IF EXISTS pgcrypto;
CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE users
(
	ID int PRIMARY KEY,
	Password varchar(40),
	FirstName varchar(50),
	LastName varchar(50),
	EmailAddress varchar(90),
	LastAccess date,
	EnrollDate date,
	Enabled boolean,
	Type char(1)
);

--student
INSERT INTO users(ID, Password, FirstName, LastName, EmailAddress, LastAccess, EnrollDate, Enabled, Type)
VALUES(234908108, encode(digest('123456', 'sha1'), 'hex'), 'Grayson', 'Loss', 'grayson.loss1@gmail.com',  '2021-02-09', '2022-01-09', true, 's');

INSERT INTO users(ID, Password, FirstName, LastName, EmailAddress, LastAccess, EnrollDate, Enabled, Type)
VALUES(100111111, encode(digest('s83mhLi?3', 'sha1'), 'hex'), 'Mike', 'Jones', 'mike.jones@gmail.com', '2022-01-04', '2023-02-09', true, 's');

INSERT INTO users(ID, Password, FirstName, LastName, EmailAddress, LastAccess, EnrollDate, Enabled, Type)
VALUES(100832859, encode(digest('54Gba7C!', 'sha1'), 'hex'), 'Ethan', 'Chen', 'chensong108330@gmail.com', '2021-02-01', '2022-02-05', true, 's');

INSERT INTO users(ID, Password, FirstName, LastName, EmailAddress, LastAccess, EnrollDate, Enabled, Type)
VALUES(100157800, encode(digest('mypassword', 'sha1'), 'hex'), 'Josh', 'Zolotorev', 'joshzolotorev@gmail.com', '2021-02-01', '2022-02-05', true, 's');

--faculty
INSERT INTO users(ID, Password, FirstName, LastName, EmailAddress, LastAccess, EnrollDate, Enabled, Type)
VALUES(107239897, encode(digest('1082Hb3*7', 'sha1'), 'hex'), 'Kathleen', 'Benayon', 'kathleenb234@gmail.com',  '2021-09-19', '2022-01-04', true, 'f');

INSERT INTO users(ID, Password, FirstName, LastName, EmailAddress, LastAccess, EnrollDate, Enabled, Type)
VALUES(104729811, encode(digest('h*j209AN3', 'sha1'), 'hex'), 'Andrew', 'Wong', 'tehtechie@protonmail.com', '2022-08-09', '2023-03-15', true, 'f');

INSERT INTO users(ID, Password, FirstName, LastName, EmailAddress, LastAccess, EnrollDate, Enabled, Type)
VALUES(100703889, encode(digest('s83mhLi?3', 'sha1'), 'hex'), 'Allyson', 'Chen', 'gacc108@gmail.com', '2021-03-04', '2022-4-01', true, 'f');

INSERT INTO users(ID, Password, FirstName, LastName, EmailAddress, LastAccess, EnrollDate, Enabled, Type)
VALUES(100721899, encode(digest('s83mhLi?3', 'sha1'), 'hex'), 'Sam', 'Freedman', 'samfreedman@gmail.com', '2021-06-04', '2022-07-01', true, 'f');

--admin
INSERT INTO users(ID, Password, FirstName, LastName, EmailAddress, LastAccess, EnrollDate, Enabled, Type)
VALUES(100000000, encode(digest('password', 'sha1'), 'hex'), 'admin', 'user', 'admin@gmail.com',  '2021-02-09', '2022-01-09', true, 'a');




CREATE TABLE students
(
	ID int PRIMARY KEY,
	ProgramCode varchar(8),
	ProgramDescription varchar(50),
	Year int,
	FOREIGN KEY (ID) REFERENCES users(ID)
);

INSERT INTO students(ID, ProgramCode, ProgramDescription, Year)
VALUES(234908108, 'NETD3201', 'Net Development II', 2022);

INSERT INTO students(ID, ProgramCode, ProgramDescription, Year)
VALUES(100111111, 'WEBD4201', 'Web Development - Enterprise', 2023);

INSERT INTO students(ID, ProgramCode, ProgramDescription, Year)
VALUES(100832859, 'SYDE2201', 'Systems Development I', 2022);

INSERT INTO students(ID, ProgramCode, ProgramDescription, Year)
VALUES(100157800, 'MAFD4201', 'Mainframe Development I', 2017);



CREATE TABLE faculty
(
	ID int PRIMARY KEY,
	SchoolCode varchar(8),
	SchoolDescription varchar(50),
	Office VARCHAR(50),
	Extension int,
	FOREIGN KEY (ID) REFERENCES users(ID)
);


INSERT INTO faculty(ID, SchoolCode, SchoolDescription, Office, Extension)
VALUES(107239897, 'UWO', 'Western University', 'B281', 222);

INSERT INTO faculty(ID, SchoolCode, SchoolDescription, Office, Extension)
VALUES(104729811, 'YU', 'York University', 'D180', 222);

INSERT INTO faculty(ID, SchoolCode, SchoolDescription, Office, Extension)
VALUES(100703889, 'OTU', 'Ontario Tech University', 'A477', 222);

INSERT INTO faculty(ID, SchoolCode, SchoolDescription, Office, Extension)
VALUES(100721899, 'WLU', 'Wilfred Laurier University', 'G491', 222);

