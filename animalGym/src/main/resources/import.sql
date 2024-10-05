INSERT INTO periodos (id, periodo, nombre) VALUES(1, 7, 'Semana');
INSERT INTO periodos (id, periodo, nombre) VALUES(2, 15, 'Quincenal');
INSERT INTO periodos (id, periodo, nombre) VALUES(3, 30, 'Mes');
INSERT INTO periodos (id, periodo, nombre) VALUES(4, 60, 'Bimestre');
INSERT INTO periodos (id, periodo, nombre) VALUES(5, 90, 'Trimestre');
INSERT INTO periodos (id, periodo, nombre) VALUES(6, 180, 'Semestre');
INSERT INTO periodos (id, periodo, nombre) VALUES(7, 365, 'Anual');

INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus, username, role_user, existe) VALUES(1, 'alex', 'locia', '2024-01-18', '7471', 'alex@mail.com', '', 1203, 7, true, 'admin', 'ROLE_ADMIN', true);
INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_inicio, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus, existe) VALUES(2, 'bryan', 'lopez', '2023-09-22', '2024-01-22', '837636', 'bryan@mail.com', '', 1111, 15, false, true);
INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus, existe) VALUES(3, 'wilianismo', 'cri',  '2023-03-12', '64747', 'wiliam@mail.com', '', 8621, 30, false, true);
INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus, existe) VALUES(4, 'andres', 'gordo', '2023-12-29', '7471', 'alex@mail.com', '', 1204, 7, true, true);
INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus, existe) VALUES(5, 'panchogro', 'lopez', '2023-09-22', '837636', 'bryan@mail.com', '', 1205, 15, false, true);
INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus, existe) VALUES(6, 'erika', 'salmeda',  '2023-03-12', '64747', 'wiliam@mail.com', '', 1206, 30, false, true);
INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus, existe) VALUES(1, 'samehada', 'val', '2023-12-27', '7471', 'alex@mail.com', '', 1207, 7, true, true);
INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus, existe) VALUES(2, 'juan', 'el pana', '2023-09-22', '837636', 'bryan@mail.com', '', 1208, 15, false, true);
INSERT INTO clientes (periodo_id, nombre, apellidos, fecha_fin, telefono, correo, foto, num_control, dias_periodo, estatus, existe) VALUES(3, 'atz', 'alv',  '2023-03-12', '64747', 'wiliam@mail.com', '', 1209, 30, false, true);

INSERT INTO usuarios (nombre, apellidos, email, username, password, enabled) VALUES('supremo', 'kaiosama', 'sokaio@mail.com', 'user', '$2a$10$lm8H0.J3pi1FpM3s3CUpe.Ym0FnlwHFclzGIGoWItLCN1vIFWEMmm', 1);
INSERT INTO usuarios (nombre, apellidos, email, username, password, enabled) VALUES('andres', 'gordo', 'angor@mail.com', 'admin', '$2a$10$g2//5daw/XMhgjPbkryr7OL5h05zauUHrNhBL8ey6V0NNQHV/3dGm', 1);
INSERT INTO usuarios (nombre, apellidos, email, username, password, enabled) VALUES('admin2', 'admin2', 'admin@mail.com', 'admin2', '$2a$10$g2//5daw/XMhgjPbkryr7OL5h05zauUHrNhBL8ey6V0NNQHV/3dGm', 1);

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES(1, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES(2, 2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES(3, 2);

INSERT INTO historiales(fecha_visita, nombre, apellidos, hora_visita) VALUES('2024-01-23', 'alex', 'locia', '');
INSERT INTO historiales(fecha_visita, nombre, apellidos, hora_visita) VALUES('2024-01-23', 'alex1', 'locia1', '');
INSERT INTO historiales(fecha_visita, nombre, apellidos, hora_visita) VALUES('2024-01-22', 'alex2', 'locia2', '');
INSERT INTO historiales(fecha_visita, nombre, apellidos, hora_visita) VALUES('2024-01-21', 'alex3', 'locia3', '');
INSERT INTO historiales(fecha_visita, nombre, apellidos, hora_visita) VALUES('2024-01-20', 'alex4', 'locia4', '');
INSERT INTO historiales(fecha_visita, nombre, apellidos, hora_visita) VALUES('2024-01-20', 'alex5', 'locia5', '');
INSERT INTO historiales(fecha_visita, nombre, apellidos, hora_visita) VALUES('2024-01-19', 'alex6', 'locia6', '');
INSERT INTO historiales(fecha_visita, nombre, apellidos, hora_visita) VALUES('2024-01-19', 'alex7', 'locia7', '');
INSERT INTO historiales(fecha_visita, nombre, apellidos, hora_visita) VALUES('2024-01-18', 'alex8', 'locia8', '');
INSERT INTO historiales(fecha_visita, nombre, apellidos, hora_visita) VALUES('2024-01-18', 'alex8', 'locia8', '');

/* Productos */
INSERT INTO productos(nombre, precio, stock, descripcion, fecha_registro) VALUES('Proteina 1', 1200, 5, 'Whey', NOW());
INSERT INTO productos(nombre, precio, stock, descripcion, fecha_registro) VALUES('Creatina 1', 650, 3, 'Mono', NOW());
INSERT INTO productos(nombre, precio, stock, descripcion, fecha_registro) VALUES('Aminoacidos 1', 800, 8, 'Bcas', NOW());

INSERT INTO ventas(nota, fecha_venta, hora_venta, usuario_id) VALUES('Nota 1', NOW(), NOW(), 2);
INSERT INTO detalle_ventas(cantidad, venta_id, producto_id) VALUES(2, 1, 1);
INSERT INTO detalle_ventas(cantidad, venta_id, producto_id) VALUES(3, 1, 2);

