import {  Connection } from 'typeorm';
import { StoreUsuarioDAO } from '../DAO/StoreUsuarioDAO';
import  * as sha255 from "sha256"; //npm install --save sha256


import UserSessionDTO  from '../DTO/UserSessionDTO';
import { Store_usuario } from '../entity/Store_usuario';
import { StoreSucursalDAO } from '../DAO/StoreSucursalDAO';

export class UsuarioServer{
        
    resourcesUsuario= (app:any,conn:Connection):void=>{
        const SALT = "3d777fc8f098c8c18e457560352c4c619296801b65614cb9e49ec005aee55352";
        
        
        app.post('/login',async (req:any, res:any)=> {
            console.log(req)
            res.send(await this.getUserSession(req.body, SALT));
        });
        
    }
    
    private getUserSession = async(params : any, SALT:string):Promise<UserSessionDTO>=>{
        const usuarioDAO = new StoreUsuarioDAO();
        const sucursalDAO = new StoreSucursalDAO();
        var usrSessionObj:UserSessionDTO = new UserSessionDTO();
        
        if(params && typeof params.passwd !== 'undefined'){
            var passwd = params.passwd+SALT;
            
            params.passwd = sha255(passwd);
            console.log(params.passwd);
        }

        const usrObj= await usuarioDAO.getUsrByUsrAndPasswd(params).catch(function(error) {
            console.log('Caught!', error);
        });
        if(usrObj !== undefined){

            if(usrObj.bloqueado ==1){
                usrSessionObj.response.message = "Usuario bloqueado. Contacte al administrador";
                usrSessionObj.response.status = "ERROR";
                usrSessionObj.response.code=-1;
                return usrSessionObj;
            }

            if(usrObj.activo == 0){
                usrSessionObj.response.message = "Usuario dado de baja. Contacte al administrador";
                usrSessionObj.response.status = "ERROR";
                usrSessionObj.response.code=-1;
                return usrSessionObj;
            }

            usrSessionObj.id_usr = usrObj.id_usr;
            usrSessionObj.login = usrObj.login;
            usrSessionObj.nombre = usrObj.nombre;
            usrSessionObj.correo = usrObj.correo;
            usrSessionObj.telefono = usrObj.telefono;
            usrSessionObj.direccion = usrObj.direccion;
            usrSessionObj.id_perfil = usrObj.id_perfil;
            usrSessionObj.id_sucursal = usrObj.id_sucursal;

            usrSessionObj.sucursalUsr = await sucursalDAO.getSucById(usrObj.id_sucursal);
            usrSessionObj.response.status = "OK";
        }else{
            usrSessionObj.response.message = "Usuario no encontrado, valide la contrase\u00F1a y el usuario";
            usrSessionObj.response.status = "ERROR";
            usrSessionObj.response.code=-1;
        }

        return usrSessionObj;
    }
}