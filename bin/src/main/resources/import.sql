/*Usuarios registrados*/
INSERT INTO usuarios (id, username, password, enabled, nombre, apellido, email, poblacion, pais, telefono, is_jefe) VALUES (1, 'admin', '$2a$10$r/KeojpPM4r7vyrJZPFO..ARRrpSWdLVLhgXN7oY9DU/2Inn8b9xG', true, 'Luis Antonio', 'Morales Mena', 'lamorales@gmail.com', 'Madrid', 'España', '654456543', false);
INSERT INTO usuarios (id, username, password, enabled, nombre, apellido, email, poblacion, pais, telefono, is_jefe) VALUES (2, 'fulanito', '$2a$10$QBPs5rVwrMHJ0PJkOj2cUuEZuqNuq8qWXfxV3EPDcYb.PTvYoX16q', true, 'Maria', 'Ceballos', 'luispastor75@gmail.com', 'Oviedo', 'España', '660900123', true);
INSERT INTO usuarios (id, username, password, enabled, nombre, apellido, email, poblacion, pais, telefono, is_jefe) VALUES (3, 'casculi', '$2a$10$1BAj42N6onZjSCYcTwUjm./ftt7WiZSsAKl42KZ0Oe.T6OzNJbyWe', true, 'Carlos Ángel', 'Muñoz Prieto', 'camuñoz45@hotmail.es', 'Santa Pola', 'España', '669343201', true);
INSERT INTO usuarios (id, username, password, enabled, nombre, apellido, email, poblacion, pais, telefono, is_jefe) VALUES (4, 'menganito', '$2a$10$FqQzY1l2Bp7qUkLIrVITtuA5KHkAhWMGet1BOep5mvx8pdwU4xAwK', true, 'Pedro', 'Heredia', 'pheredia@mixmail.com', 'Ciudad Real', 'España', '623099020', false);
INSERT INTO usuarios (id, username, password, enabled, nombre, apellido, email, poblacion, pais, telefono, is_jefe) VALUES (5, 'tecnologias', '$2a$10$9dSFVnFAXV4BhSTxNXUl3evtv208iY0tyGYx.PqgiP86wdbt1gZ2e', true, 'Isabel', 'González', 'iglonzalez23@yahoo.es', 'Molina de Aragón', 'España', '605894501', false);
INSERT INTO usuarios (id, username, password, enabled, nombre, apellido, email, poblacion, pais, telefono, is_jefe) VALUES (6, 'chuva', '$2a$10$99f7GstwXNMESec.gKl6KeNvyzm.1Xhnr1oNAZ/KUDD59BYWeO30a', true, 'Maria del Carmen', 'López Fraile', 'lopezfraile3423@hotmail.es', 'Berlín', 'Alemania', '609563212', false);
INSERT INTO usuarios (id, username, password, enabled, nombre, apellido, email, poblacion, pais, telefono, is_jefe) VALUES (7, 'lorenzo99', '$2a$10$A/dMmXjKbRKRRyzIgK9eRupUhBvuV2PweoPBtuX2DXE2bOtEVSfte', true, 'José María', 'De las Heras', 'ximoheras43@hotmail.com', 'Orense', 'España', '620345810', false);
INSERT INTO usuarios (id, username, password, enabled, nombre, apellido, email, poblacion, pais, telefono, is_jefe) VALUES (8, 'lopesino', '$2a$10$KYV0eiY97QtTw3zY16AqZeA7Bozf3B1sYeamJNq/od.iVdNsqlFJm', true, 'Fernando', 'Campoamor', 'lopesino56@ya.com', 'Tossa de Mar', 'España', '643540910', true);
INSERT INTO usuarios (id, username, password, enabled, nombre, apellido, email, poblacion, pais, telefono, is_jefe) VALUES (9, 'lajarin20', '$2a$10$IPlPNUEXhu9/4r3WTQ5JieFE0diCfbQY3yaYrz2Dx.nHKTXj1hykW', true, 'Antonio Jesús', 'Fernández Pastor', 'lajarintony@ozu.es', 'Salamanca', 'España', '690230912', false);
INSERT INTO usuarios (id, username, password, enabled, nombre, apellido, email, poblacion, pais, telefono, is_jefe) VALUES (10, 'peter32', '$2a$10$GYSrfzeww9gTxjJIqqrQ4.z7clVZIM55SesGMzS/OAPisG4zuLOhW', true, 'Pedro Luis', 'Serrano', 'serranomorales32@hotmail.com', 'Alcalá de Henares', 'España', '623430920', false);
INSERT INTO usuarios (id, username, password, enabled, nombre, apellido, email, poblacion, pais, telefono, is_jefe) VALUES (11, 'casiano', '$2a$10$.WVSJRIWEzeLhI0Ev3V7HeIlgTKooWPSHiGdjGZAjy0C33ay888Ru', true, 'Julio', 'Pérez García', 'josegilartealvarez@gmail.com', 'Toledo', 'España', '620549839', true);
INSERT INTO usuarios (id, username, password, enabled, nombre, apellido, email, poblacion, pais, telefono, is_jefe) VALUES (12, 'virgi75', '$2a$10$.WVSJRIWEzeLhI0Ev3V7HeIlgTKooWPSHiGdjGZAjy0C33ay888Ru', true, 'Virginia', 'Redondo', 'virgiredondo90@gmail.es', 'Madrid', 'España', '667909321', false);

/*Authorities*/
INSERT INTO authorities (user_id, authority) VALUES (1,'ROLE_ADMIN');
INSERT INTO authorities (user_id, authority) VALUES (2,'ROLE_JEFE');
INSERT INTO authorities (user_id, authority) VALUES (2,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (3,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (3,'ROLE_JEFE');
INSERT INTO authorities (user_id, authority) VALUES (4,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (5,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (6,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (7,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (8,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (8,'ROLE_JEFE');
INSERT INTO authorities (user_id, authority) VALUES (9,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (10,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (11,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (11,'ROLE_JEFE');
INSERT INTO authorities (user_id, authority) VALUES (12,'ROLE_USER');

/*Salas registradas*/
INSERT INTO salas (id, nombre, aforo, disponible, streaming, wifi, grabacion, megafonia, presentacion, apertura, cierre) VALUES (1, 'Avip1', 5, true, true, true, true, false, false, '10:30', '18:50');
INSERT INTO salas (id, nombre, aforo, disponible, streaming, wifi, grabacion, megafonia, presentacion, apertura, cierre) VALUES (2, 'Aula de informática', 9, true, true, true, true, false, false, '08:00', '15:00');
INSERT INTO salas (id, nombre, aforo, disponible, streaming, wifi, grabacion, megafonia, presentacion, apertura, cierre) VALUES (3, 'Sala magna', 30, true, false, false, true, true, false, '09:00', '17:30');
INSERT INTO salas (id, nombre, aforo, disponible, streaming, wifi, grabacion, megafonia, presentacion, apertura, cierre) VALUES (4, 'Aula de exámenes', 12, true, false, true, true, true, true, '11:00', '20:45');
INSERT INTO salas (id, nombre, aforo, disponible, streaming, wifi, grabacion, megafonia, presentacion, apertura, cierre) VALUES (5, 'Jacinto Verdaguer', 18, true, false, false, true, true, false, '09:30', '20:50');
INSERT INTO salas (id, nombre, aforo, disponible, streaming, wifi, grabacion, megafonia, presentacion, apertura, cierre) VALUES (6, 'Avip2', 10, true, true, true, true, false, false, '09:30', '15:50');
INSERT INTO salas (id, nombre, aforo, disponible, streaming, wifi, grabacion, megafonia, presentacion, apertura, cierre) VALUES (7, 'Sala de lectura', 25, true, false, false, true, true, true, '12:30', '18:30');
INSERT INTO salas (id, nombre, aforo, disponible, streaming, wifi, grabacion, megafonia, presentacion, apertura, cierre) VALUES (8, 'After hours', 30, true, false, false, true, false, true, '00:30', '23:30');
INSERT INTO salas (id, nombre, aforo, disponible, streaming, wifi, grabacion, megafonia, presentacion, apertura, cierre) VALUES (9, 'Avip3', 8, false, true, false, false, true, false, '15:30', '21:30');
INSERT INTO salas (id, nombre, aforo, disponible, streaming, wifi, grabacion, megafonia, presentacion, apertura, cierre) VALUES (10, 'Sala 11', 10, false, false, true, true, true, false, '09:30', '19:30');

/*Contactos*/
INSERT INTO contactos (id, alias, email, usuario_id, create_at) VALUES (1,'charles', 'carlosmedina@telefonica.net', 2, '2019-04-18');
INSERT INTO contactos (id, alias, email, usuario_id, create_at) VALUES (2,'cabillo', 'cabillo@mixmail.com', 2, '2019-04-19');
INSERT INTO contactos (id, alias, email, usuario_id, create_at) VALUES (3,'goonie', 'gbartolo@gmail.com', 2, '2020-04-09');
INSERT INTO contactos (id, alias, email, usuario_id, create_at) VALUES (4,'fabiola', 'fabiromero79@omrom.net', 2, '2018-09-20');
INSERT INTO contactos (id, alias, email, usuario_id, create_at) VALUES (5,'caesar', 'cesaralberca@hotmail.es', 4, '2017-03-01');
INSERT INTO contactos (id, alias, email, usuario_id, create_at) VALUES (6,'cabillo', 'cabillo@mixmail.com', 4, '2019-07-09');
INSERT INTO contactos (id, alias, email, usuario_id, create_at) VALUES (7,'living91', 'living91@gmail.com', 4, '2017-02-21');
INSERT INTO contactos (id, alias, email, usuario_id, create_at) VALUES (8,'pavino', 'gargantiel91@mapei.net', 2, '2018-01-19');
INSERT INTO contactos (id, alias, email, usuario_id, create_at) VALUES (9,'sanabria23', 'davidsanabria23@hotmail.com', 2, '2019-06-20');
INSERT INTO contactos (id, alias, email, usuario_id, create_at) VALUES (10,'serranillo', 'charlysierra@gmail.com', 2, '2019-11-25');
INSERT INTO contactos (id, alias, email, usuario_id, create_at) VALUES (11,'lixben10', 'domingruta@mixmail.com', 2, '2020-03-25');
INSERT INTO contactos (id, alias, email, usuario_id, create_at) VALUES (12,'tripi', 'carrillopedro@hotmail.com', 2, '2020-04-16');
INSERT INTO contactos (id, alias, email, usuario_id, create_at) VALUES (13,'roger20', 'medinalopez67@orange.com', 2, '2017-12-29');
INSERT INTO contactos (id, alias, email, usuario_id, create_at) VALUES (14,'charles', 'carlosmedina@telefonica.net', 4, '2018-01-30');
INSERT INTO contactos (id, alias, email, usuario_id, create_at) VALUES (15,'perico11', 'pedrobulillas32@masmovil.net', 2, '2020-02-29');
INSERT INTO contactos (id, alias, email, usuario_id, create_at) VALUES (16,'capataz32', 'lorenzofelipe32@gmail.com', 2, '2020-01-31');
INSERT INTO contactos (id, alias, email, usuario_id, create_at) VALUES (17,'capataz32', 'lorenzofelipe32@gmail.com', 4, '2020-02-18');