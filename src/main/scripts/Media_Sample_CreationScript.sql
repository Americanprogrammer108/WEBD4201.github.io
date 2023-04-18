DROP TABLE IF EXISTS music;
DROP TABLE IF EXISTS media;
DROP TABLE IF EXISTS clientRoles;
DROP TABLE IF EXISTS clients;


CREATE TABLE IF NOT EXISTS clients (
	username varchar(16) PRIMARY KEY,
	firstName varchar(25) NOT NULL,
	lastName varchar(25) NOT NULL,
	email varchar(75),
	isActive boolean DEFAULT true
	);
	
CREATE TABLE IF NOT EXISTS clientRoles (
	username varchar(16) PRIMARY KEY,
	isUser boolean DEFAULT false,
	isAdmin boolean DEFAULT false,
	isDev boolean DEFAULT false,
	CONSTRAINT fk_clients_roles FOREIGN KEY (username) REFERENCES clients(username)
);

CREATE TABLE media (
	id int PRIMARY  KEY,
	title varchar(35) NOT NULL,
	media_type char(2) CHECK (media_type IN ('mu','mo','tv','vg','sm')),
	length decimal(9,1) DEFAULT 0,
	genre varchar(20),
	views int DEFAULT 0,
	enabled boolean DEFAULT true
);

CREATE TABLE music (
	id int PRIMARY KEY,
	artist varchar(35) NOT NULL,
	album varchar(50),
	rpm int,
	CONSTRAINT music_media_fk FOREIGN KEY (id) REFERENCES media(id)
);

INSERT INTO media VALUES (1, 'Believer', 'mu', 3.25, 'Pop Rock', 123, true);
INSERT INTO music VALUES (1, 'Imagine Dragons', 'Evolve', 110);