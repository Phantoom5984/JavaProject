drop database if exists  biblioteca;
create database biblioteca;
use biblioteca;
create table usuarios(
usuario VARCHAR(50) primary key,
contrasenya VARCHAR(50),
adm VARCHAR(50) default 'No'
);
create table autor(
id int auto_increment primary key ,
nombre VARCHAR(100)
);
create table libros(
id int auto_increment primary key,
titulo VARCHAR(200),
editorial VARCHAR(100),
fecha VARCHAR(100),
id_autor int,
constraint fk_idautor foreign key (id_autor) references autor (id) on update cascade on delete cascade
);
create table copias(
id int auto_increment primary key,
cantidad int,
id_libro int,
constraint fk_idlibro foreign key (id_libro) references libros (id) on update cascade on delete cascade
);
insert into usuarios values(1,1,'Si');
insert into autor (nombre) values('Lorca');
insert into autor (nombre) values('Cervantes');
insert into autor (nombre) values('Dickens');
insert into autor (nombre) values('Conrad');
insert into libros (titulo, editorial, fecha, id_autor) values('Romancero gitano', 'Alianza editorial','1928', 1);
insert into libros (titulo, editorial, fecha, id_autor) values('Poeta en Nueva York', 'Visor Libros','1940', 1);
insert into libros (titulo, editorial, fecha, id_autor) values('Canciones', 'Cuatro lunas','1936', 1);
insert into libros (titulo, editorial, fecha, id_autor) values('Escritos Flamencos', 'Casimiro','1989', 1);
insert into libros (titulo, editorial, fecha, id_autor) values('Don Quijote de la Mancha', 'Pluton ediciones','1605/1615', 2);
insert into libros (titulo, editorial, fecha, id_autor) values('Novelas ejemplares', 'Catedra','1613', 2);
insert into libros (titulo, editorial, fecha, id_autor) values('La Galatea', 'Ibericas','1585', 2);
insert into libros (titulo, editorial, fecha, id_autor) values('Entremeses', 'Bambu','1615', 2);
insert into libros (titulo, editorial, fecha, id_autor) values('Oliver Twist', 'Alianza editorial','1838', 3);
insert into libros (titulo, editorial, fecha, id_autor) values('Grandes esperanzas', 'Penguin Classics','1861', 3);
insert into libros (titulo, editorial, fecha, id_autor) values('Cancion de navidad', 'Bruño','1843', 3);
insert into libros (titulo, editorial, fecha, id_autor) values('Cuentos de navidad', 'Alianza editorial','1843', 3);
insert into libros (titulo, editorial, fecha, id_autor) values('Nostromo', 'Alianza editorial','1904', 4);
insert into libros (titulo, editorial, fecha, id_autor) values('El corazón de las tinieblas', 'Editorial Juventud','1899', 4);
insert into libros (titulo, editorial, fecha, id_autor) values('Lord Jim', 'Alianza editorial','1900', 4);
insert into libros (titulo, editorial, fecha, id_autor) values('El agente secreto', 'Alianza editorial','1907', 4);
insert into copias (cantidad, id_libro) values(5,1);
insert into copias (cantidad, id_libro) values(5,2);
insert into copias (cantidad, id_libro) values(5,3);
insert into copias (cantidad, id_libro) values(5,4);
insert into copias (cantidad, id_libro) values(6,5);
insert into copias (cantidad, id_libro) values(6,6);
insert into copias (cantidad, id_libro) values(6,7);
insert into copias (cantidad, id_libro) values(6,8);
insert into copias (cantidad, id_libro) values(7,9);
insert into copias (cantidad, id_libro) values(7,10);
insert into copias (cantidad, id_libro) values(7,11);
insert into copias (cantidad, id_libro) values(7,12);
insert into copias (cantidad, id_libro) values(8,13);
insert into copias (cantidad, id_libro) values(8,14);
insert into copias (cantidad, id_libro) values(8,15);
insert into copias (cantidad, id_libro) values(8,16);
select * from autor;
select * from libros;
select * from copias;
select * from usuarios;