INSERT INTO status (name) VALUES ('Activo'), ('Inactivo'), ('Suspendido'), ('Ocupado');

INSERT INTO users (username,first_name,last_name,mother_last_name,email,password,phone,photo_ex,enabled,status_id,create_at) VALUES ('david','David', 'Leones', 'Colina', 'david.leones@correo.cl', '$2a$10$0M61aGvS506iGO7oiJrKsOMC2GFTbr9Icsh2x9ZvL2Ykk7VLCx0X.', '+569999999', 'ruta fake', true, 1, now() );
INSERT INTO users (username,first_name,last_name,email,password,enabled,status_id,create_at) VALUES ('jhoa','Jhoa', 'Leones', 'Jhoa.leones@correo.cl', '$2a$10$0M61aGvS506iGO7oiJrKsOMC2GFTbr9Icsh2x9ZvL2Ykk7VLCx0X.', true, 1, now() );
INSERT INTO users (username,first_name,last_name,mother_last_name,email,password,enabled,status_id,create_at) VALUES ('lili','Lisseth', 'Leones', 'Colina', 'lisseth.leones@correo.cl', '$2a$10$0M61aGvS506iGO7oiJrKsOMC2GFTbr9Icsh2x9ZvL2Ykk7VLCx0X.', true, 1, now() );


/* ROLEs */

insert into role (name) values ('ROLE_USER'),('ROLE_ADMIN');
insert into privilege (user_id, role_id) values (1,1),(1,2),(2,1);