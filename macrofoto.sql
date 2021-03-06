drop database macrofoto;
create database macrofoto;
use macrofoto;

drop table if EXISTS store_sucursal;
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

ALTER TABLE store_sucursal ADD prefijo varchar(5) NOT NULL ;
update store_sucursal set prefijo='IRP' WHERE id_sucursal=1;
update store_sucursal set prefijo='OAX' WHERE id_sucursal=2;
update store_sucursal set prefijo='PDD' WHERE id_sucursal=3;
update store_sucursal set prefijo='SAL' WHERE id_sucursal=4;

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
insert into store_fotografo(fotografo,telefono,email,estatus) 
VALUES('fotografo 1','000000000','correo@correo.com',1),
('fotografo 2','000000000','correo@correo.com',1),
('fotografo 3','000000000','correo@correo.com',1),
('fotografo 4','000000000','correo@correo.com',1),
('Amairani Garcia','000000000','correo@correo.com',1);

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

ALTER TABLE `macrofoto`.`store_menu` 
ADD COLUMN `fx_id` VARCHAR(15) NULL AFTER `uri`;
UPDATE `macrofoto`.`store_menu` SET `fx_id`='itemVenta' WHERE `id_menu`='1';
UPDATE `macrofoto`.`store_menu` SET `fx_id`='itemAdmin' WHERE `id_menu`='2';
UPDATE `macrofoto`.`store_menu` SET `menu_desc`='Gestion de Productos', `uri`='gestProd', `fx_id`='lblGestionCat' WHERE `id_menu`='3';
UPDATE `macrofoto`.`store_menu` SET `menu_desc`='Gestion Costo Productos', `uri`='gestCostProd', `fx_id`='lblGestCostProd' WHERE `id_menu`='4';
INSERT INTO `macrofoto`.`store_menu` (`id_menu`, `id_padre`, `menu_desc`, `uri`, `fx_id`, `estatus`) VALUES ('5', '2', 'Consulta Reportes', 'consultaReporte', 'lblReportes', '1');

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
insert into store_menu_perfil VALUES(2,5);
drop table if exists store_usuario;
create table store_usuario(
id_usr INT AUTO_INCREMENT PRIMARY KEY,
login VARCHAR(11) NOT NULL UNIQUE,
passwd VARCHAR(50) NOT NULL,
nombre VARCHAR(150),
correo VARCHAR(150),
telefono INT(10),
direccion VARCHAR(200),
intentos INT(1),
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
insert into store_usuario(login,passwd,nombre,correo,telefono,direccion,intentos,bloqueado,activo,id_perfil,id_sucursal)
values('ayalaja','macro123','J. Alberto AC','correo@mail.com','0000000000','calle 1 col colonia #123',0,0,1,2,1),
('garciaa','macro123','Amairani Garcia S','correo@mail.com','0000000000','calle 1 col colonia #123',0,0,1,2,2),
('vendedor','venta123','Vendedor AP','correo@mail.com','0000000000','N/A',0,0,1,2,3);

create table store_cat_estatus(
id_estatus INT AUTO_INCREMENT PRIMARY KEY,
estatus VARCHAR(20)
);
insert into store_cat_estatus(estatus)
VALUES('Terminado'),('Pendiente'),('Cancelado');

drop table store_pedido;
create table store_pedido(
id_pedido INT AUTO_INCREMENT PRIMARY KEY,
folio VARCHAR(100),
cliente VARCHAR(100),
telefono VARCHAR(10),
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
insert into store_pedido(folio,cliente,telefono,descripcion,fec_pedido,fec_entregado,monto_ant,monto_total,id_estatus)
value('MCOIQ-200128000001','Amairani Garcia','0000000000','','2020-01-28',null,14.5,22.5,2),
	('MCOIQ-200128000002','Amairani Garcia','0000000000','pedido incorrecto','2020-01-28',null,0,0,3);

drop table store_prod_pedido;
create table store_prod_pedido(
id_prod_pedido INT AUTO_INCREMENT PRIMARY KEY,
bar_code VARCHAR(15),
descripcion VARCHAR(150),
cantidad INT(10),
costo_unitario DECIMAL(10,2),
costo_total DECIMAL(10,2),
#id_prod INT,
id_pedido INT,
/*CONSTRAINT `FK_prod_prodPedido`
    FOREIGN KEY (`id_prod`)
    REFERENCES store_cliente_prod_cost (`id_clte_prod_cost`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,*/
CONSTRAINT `FK_pedido_prodPedido`
    FOREIGN KEY (`id_pedido`)
    REFERENCES store_pedido (`id_pedido`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

insert into store_prod_pedido(bar_code,descripcion,cantidad,costo_unitario,costo_total,id_pedido)
value('MCO00000008','Fotos_Infantiles',6,'150','900',1),
	('MCO00000010','Marcos_3x4_Modura_Aluminio',1,'400','400',1),
	('MCO01260508','Lamina',4,'80','320',1),
	('MCO00000083','Marcos_5x10',2,'55','110',2);
    
select * from store_prod_pedido;