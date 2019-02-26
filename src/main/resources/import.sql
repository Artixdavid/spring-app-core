INSERT INTO status (name) VALUES ('Activo'), ('Inactivo'), ('Suspendido'), ('Ocupado');

INSERT INTO users (username,first_name,last_name,mother_last_name,email,password,phone,photo_ex,enabled,status_id,create_at, about_me, second_name, fecha_nacimiento, address) VALUES ('david','franklin', 'Leones', 'Colina', 'david.leones@correo.cl', '$2a$10$0M61aGvS506iGO7oiJrKsOMC2GFTbr9Icsh2x9ZvL2Ykk7VLCx0X.', '+569999999', 'ruta fake', true, 1, now(), 'Ingeniero de Sistemas, líder de proyectos de desarrollo de software, donde siempre se consideran los ciclos de vida del sistema, su mantenimiento y soporte a lo largo plazo. Ademas poseo experiencia en diseño y modelado de base de datos relacionales, análisis, diseño, desarrollo y mantenimiento de sistemas web. Asimismo como la automatización y optimización de procesos internos. Autodidacta y con mucha pasión por las tecnología.', 'david', now(), 'San pablo 1503' );
INSERT INTO users (username,first_name,last_name,email,password,enabled,status_id,create_at, about_me, second_name, fecha_nacimiento, address) VALUES ('jhoa','Jhoa', 'Leones', 'Jhoa.leones@correo.cl', '$2a$10$0M61aGvS506iGO7oiJrKsOMC2GFTbr9Icsh2x9ZvL2Ykk7VLCx0X.', true, 1, now(), 'Nita mayor', 'Lisbeth', now(),'Su casita' );
INSERT INTO users (username,first_name,last_name,mother_last_name,email,password,enabled,status_id,create_at, about_me, second_name, fecha_nacimiento, address) VALUES ('nita','Lisseth', 'Leones', 'Colina', 'lisseth.leones@correo.cl', '$2a$10$0M61aGvS506iGO7oiJrKsOMC2GFTbr9Icsh2x9ZvL2Ykk7VLCx0X.', true, 1, now(), 'Nita Menor', 'Coromoto', now(), 'Su casita' );


/* ROLES */

insert into role (name) values ('ROLE_USER'),('ROLE_ADMIN');
insert into privilege (user_id, role_id) values (1,1),(1,2),(2,1);