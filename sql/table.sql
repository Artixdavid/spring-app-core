CREATE TABLE status
(
	id 		bigserial not null,
	name 	varchar(30) not null,
	CONSTRAINT pk_status_id PRIMARY KEY ( id ) 
);

CREATE TABLE users
( 
	id						bigserial  NOT NULL,
	first_name 				VARCHAR(30) NOT NULL,
	last_name 				VARCHAR(30) NOT NULL,
	mother_last_name 		VARCHAR(30),
	email 					VARCHAR(30) NOT NULL,
	password 				VARCHAR(80) NOT NULL,
	phone 					VARCHAR(30),
	photo_ex 				text,
	enabled 				BOOLEAN DEFAULT false not NULL,	
	status_id 				bigint not null,
	create_at 				timestamp with time zone NOT NULL DEFAULT now(),
	CONSTRAINT pk_users_id PRIMARY KEY ( id ),
	CONSTRAINT fk_status_id FOREIGN KEY (status_id) REFERENCES status (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);