INSERT INTO status (name) VALUES ('Activo'), ('Inactivo'), ('Suspendido'), ('Ocupado');

INSERT INTO users (first_name,last_name,mother_last_name,email,password,phone,photo_ex,enabled,status_id,create_at) VALUES ('David', 'Leones', 'Colina', 'david.leones@correo.cl', '123', '+569999999', 'ruta fake', true, 1, now() );
INSERT INTO users (first_name,last_name,email,password,enabled,status_id,create_at) VALUES ('Jhoa', 'Leones', 'Jhoa.leones@correo.cl', '123', true, 1, now() );
INSERT INTO users (first_name,last_name,mother_last_name,email,password,enabled,status_id,create_at) VALUES ('Lisseth', 'Leones', 'Colina', 'lisseth.leones@correo.cl', '123', true, 1, now() );

