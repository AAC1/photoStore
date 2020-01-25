create table store_sucursal(
id_sucursal INT AUTO_INCREMENT PRIMARY KEY,
sucursal VARCHAR(50),
razon_social VARCHAR(150),
direccion VARCHAR(200),
telefono VARCHAR(10)
);

insert into store_sucursal(sucursal,razon_social,direccion,telefono)
VALUES('Macrofoto Irapuato','Gerardo Asgard García Jiménez','revolución 20 zona centro Irapuato Guanajuato. C.P. 36500',null);
insert into store_sucursal(sucursal,razon_social,direccion,telefono)VALUES
('Macrofoto Oaxaca','Carlos Yair García Jiménez','Calle Oaxaca 2000, col. Guerrero Irapuato,Guanajuato. C.P. 36550','6248639');
insert into store_sucursal(sucursal,razon_social,direccion,telefono)VALUES
('Macrofoto La Piedad','Gerardo Asgard García Jiménez ','Aquiles Serdán 128 centro la Piedad de Cabadas Michoacán C.P. 59300','3525221030');

insert into store_sucursal(sucursal,razon_social,direccion,telefono)VALUES
('Macrofoto Salamanca','Carlos Yair García Jiménez','Calle Morelos 201, Centro  Salamanca Guanajuato C.P. 36700','4646488882');


create table store_perfil(
id_perfil INT AUTO_INCREMENT PRIMARY KEY,
perfil VARCHAR(70),
perfil_desc VARCHAR(140)
);
insert into store_perfil(id_perfil,perfil,perfil_desc)
VALUES (1,'VENDEDOR','Perfil para generar ventas '),
(2,'ADMINISTRADOR','Perfil con acceso a todas las secciones');

drop table if exists store_cat_prod;
create table store_cat_prod(
id_prod INT AUTO_INCREMENT PRIMARY KEY,
id_padre_prod INT default 0,
producto VARCHAR(80) NOT NULL,
estatus INT(1) NOT NULL
);
insert into store_cat_prod(producto,estatus)
VALUES('Marcos',1),('Fotos',1),('Papel Hiti',1),
('Camaras',1),('Rollos',1),('Vidrios',1),('Mini DVC',1),
('Llaveros',1),('Laminas',1);

insert into store_cat_prod(producto,id_padre_prod,estatus)
VALUES('Marco 3x4',1,1),('Marco 8x9',1,1),('Marco 5x10',1,1),
('Foto 3x4',2,1),('Estudiante',2,1),('Certificado',2,1),('Foto a color',2,1),
('Foto b/n',2,1);

insert into store_cat_prod(producto,id_padre_prod,estatus)
VALUES('Moldura Aluminio',10,1),('Moldura Madera',10,1),('Moldura Rustico Plastico',12,1),
('Moldura Lisa',12,1),('Moldura Acrilico',12,1);

insert into store_cat_prod(producto,id_padre_prod,estatus)
VALUES('Moldura Aluminio',11,1),('Moldura Madera',11,1),('Moldura Rustico Plastico',11,1),
('Moldura Lisa',11,1),('Moldura Acrilico',11,1);


select * from store_cat_prod;

create table store_fotografo(
id_fotografo INT auto_increment primary key ,
fotografo VARCHAR(100),
telefono VARCHAR(10),
email VARCHAR(80),
estatus INT(1)
);

drop table if exists store_cliente_prod_cost;

create table store_cliente_prod_cost(
id_clte_prod_cost INT PRIMARY KEY AUTO_INCREMENT,
id_cliente INT, id_prod INT,
bar_code VARCHAR(12),
costo decimal(10,2),
CONSTRAINT `FK_idProd_cltProdCost`
    FOREIGN KEY (`id_prod`)
    REFERENCES store_cat_prod (`id_prod`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

insert into store_cliente_prod_cost(id_cliente,id_prod,costo)
VALUES 
(0,3,11),(0,4,9.5),(0,5,4),(0,6,7),(0,7,8),(0,8,18),(0,9,25.5),
(1,3,15),(1,4,9),(1,5,2),(1,6,6),(1,7,5),(1,8,15),(1,9,23),
(2,3,12),(2,4,8),(2,5,2.3),(2,6,4.5),(2,7,3),(2,8,16),(2,9,18),
(3,3,7),(3,4,10),(3,5,3),(3,6,4),(3,7,2.5),(3,8,12),(3,9,20);

/*
create table store_medida_estandar(
id_medida INT AUTO_INCREMENT PRIMARY KEY,
medida_desc VARCHAR(10)
);
create table store_medida_prod(
id_prod INT NOT NULL,
id_medida INT NOT NULL,
CONSTRAINT `id_prod`
    FOREIGN KEY (`FK_medida_prod`)
    REFERENCES store_cat_prod (`id_prod`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION),
CONSTRAINT `id_medida`
    FOREIGN KEY (`FK_medida_medida`)
    REFERENCES store_medida_estandar (`id_medida`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
);
*/

create table store_menu(
id_menu INT AUTO_INCREMENT PRIMARY KEY,
id_padre INT,
menu_desc VARCHAR(90),
uri VARCHAR(30),
estatus INT(1)
);

insert into store_menu(id_menu,id_padre,menu_desc,uri,estatus)
VALUES(1,0,'Venta','venta',1),
(2,0,'Administracion','',1),
(3,2,'Gestion de productos','gestion-producto',1),
(4,2,'Consulta Reportes','consulta-reporte',1);


create table store_menu_perfil(
id_perfil INT,id_menu INT,
CONSTRAINT `FK_menuPerfil_perfil`
    FOREIGN KEY (`id_perfil`)
    REFERENCES store_perfil (`id_perfil`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
CONSTRAINT `FK_menuPerfil_menu`
    FOREIGN KEY (`id_menu`)
    REFERENCES store_menu (`id_menu`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);
insert into store_menu_perfil(id_perfil,id_menu)
VALUES(1,1),(2,1),(2,2),(2,3),(2,4);


create table store_usuario(
id_usr INT AUTO_INCREMENT PRIMARY KEY,
login VARCHAR(11) NOT NULL UNIQUE,
passwd VARCHAR(50) NOT NULL,
nombre VARCHAR(150),
correo VARCHAR(150),
telefono INT(10),
direccion VARCHAR(200),
bloqueado INT(1),
activo INT(1),
id_perfil INT,
id_sucursal INT,
CONSTRAINT `FK_usr_perfil`
    FOREIGN KEY (`id_perfil`)
    REFERENCES store_perfil (`id_perfil`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
CONSTRAINT `FK_usr_sucursal`
    FOREIGN KEY (`id_sucursal`)
    REFERENCES store_sucursal (`id_sucursal`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

create table store_cat_estatus(
id_estatus INT AUTO_INCREMENT PRIMARY KEY,
estatus VARCHAR(20)
);
insert into store_cat_estatus(estatus)
VALUES('TERMINADO'),('PENDIENTE'),('CANCELADO');

create table store_pedido(
id_pedido INT AUTO_INCREMENT PRIMARY KEY,
cliente VARCHAR(100),
folio VARCHAR(100),
descripcion VARCHAR(100),
fec_pedido DATETIME,
fec_entregado DATETIME,
monto_ant DECIMAL,
monto_total DECIMAL,
id_estatus INT,
CONSTRAINT `FK_pedido_stts`
    FOREIGN KEY (`id_estatus`)
    REFERENCES store_cat_estatus (`id_estatus`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);



