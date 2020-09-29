import { Sucursal } from './Sucursal';
import { ResponseDTO } from './ResponseDTO';

export class UserSession{
    id_usr:number ;
    login:string | null;
    nombre:string| null;
    correo:string | null;
    telefono:string | null;
    direccion:string | null;
    intentos:number ;
    bloqueado:number;
    activo:number ;
    id_perfil:number ;
    id_sucursal:number ;
    sucursalUsr:Sucursal | undefined;
    response:ResponseDTO;
    dateTime: number;
}