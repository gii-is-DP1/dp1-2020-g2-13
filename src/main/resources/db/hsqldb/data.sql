-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('mjLera','Qwerty123',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'mjLera','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('chocapi','Qwerty123',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'chocapi','pagado');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('guille','Qwerty123',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'guille','registrado');

INSERT INTO vets VALUES (1, 'James', 'Carter');
INSERT INTO vets VALUES (2, 'Helen', 'Leary');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');

-- Insertar logros
INSERT INTO logros(id, nombre,descripcion) VALUES (1, 'Hola_mundo', 'primer logro conseguido por registro');
INSERT INTO logros(id, nombre,descripcion) VALUES (2, 'Chocapi', 'Choque con pizarra');

-- Insertar usuarios
INSERT INTO usuarios(id, nombre,apellidos,localidad,colegio,email,username) VALUES (1, 'María José','Lera','Sevilla','Colegio de prueba','email@us.es','mjLera');
INSERT INTO usuarios(id, nombre,apellidos,localidad,colegio,email,username) VALUES (2, 'Jose Miguel','Pizarro','Huelva','Colegio de prueba 2','email2@us.es','chocapi');

-- Insertar pdfs
INSERT INTO pdfs(id, usuario_id, link, nombre) VALUES (1,1, 'http://www.africau.edu/images/default/sample.pdf', 'documento1');

-- Insertar videos
INSERT INTO videos(id, usuario_id, nombre, link, duracion) VALUES (1,1,'cancion', 'https://www.youtube.com/watch?v=HEydV4B6mRQ','3.14');


-- Insertar examenes
INSERT INTO examenes(id, titulos, puntuacion_maxima, puntuacion_minima, usuario_id) VALUES (1, 'prueba', 10, 0, 1);

-- Insertar usuarios_logros
INSERT INTO usuarios_logros(usuario_id, logros_id) VALUES (1, 1);
INSERT INTO usuarios_logros(usuario_id, logros_id) VALUES (1, 2);

-- Insertar usuarios_examenes
--INSERT INTO usuarios_examenes(usuario_id, examenes_id) VALUES (2, 1);

-- Insertar hilos
INSERT INTO hilos(id, nombre,categoria, usuario_id) VALUES (1, 'Hola_mundo', 'General', 1);

-- Insertar comentario
INSERT INTO comentarios(id, contenido, usuario_id, hilo_id, nivel) VALUES (1, 'Hola_mundo', 1, 1, 0);
INSERT INTO comentarios(id, contenido, usuario_id, hilo_id, nivel) VALUES (2, 'Adios_mundo', 2, 1, 0);

-- Insertar hilos_suscriptores
INSERT INTO hilos_suscriptores(hilo_id, suscriptores_id) VALUES (1, 1);


--Insertar opciones

INSERT INTO opciones(id,texto, es_Correcta) VALUES (1, 'Opción 1: las parejas promiscuas', FALSE);
INSERT INTO opciones(id,texto, es_Correcta) VALUES (2, 'Opción 2: los parejos promiscuos', TRUE);
INSERT INTO opciones(id,texto, es_Correcta) VALUES (3, 'Opción 3: el pareje promiscue', TRUE);
INSERT INTO opciones(id,texto, es_Correcta) VALUES (4, 'Opción 4: las parejas promiscuaaas', FALSE);

--Insertar tipo test

INSERT INTO tipo_tests(id) VALUES (1);
INSERT INTO tipo_tests(id) VALUES (2);


--Insertar opciones a tipotest

INSERT INTO tipo_tests_opciones(tipo_test_id, opciones_id) VALUES (1,1);
INSERT INTO tipo_tests_opciones(tipo_test_id, opciones_id) VALUES (1,2);
INSERT INTO tipo_tests_opciones(tipo_test_id, opciones_id) VALUES (2,3);
INSERT INTO tipo_tests_opciones(tipo_test_id, opciones_id) VALUES (2,4);

--Insertar preguntas


INSERT INTO preguntas(id, contenido, tipo_test_id) VALUES (1, 'Pregunta de ejemplo nº 1',1);
INSERT INTO preguntas(id, contenido, tipo_test_id) VALUES (2, 'Pregunta de ejemplo nº 2',2);


-- Añadir pregunta a examen
INSERT INTO examenes_preguntas(examen_id, preguntas_id) VALUES (1,1);
INSERT INTO examenes_preguntas(examen_id, preguntas_id) VALUES (1,2);


-- Insertar mensajes privados
INSERT INTO mensajes_privados(id, contenido, emisor_id, receptor_id) VALUES (1, 'Hola_mundo', 2, 1);


-- Insertar notificaciones
INSERT INTO notificaciones(id, usuario_id, comentario_id, mensaje_privado_id) VALUES (1, 1, null, 1);







INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'chocapi');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'chocapi');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'chocapi');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'chocapi');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'chocapi');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'chocapi');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'chocapi');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'chocapi');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'chocapi');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'chocapi');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);

INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');

