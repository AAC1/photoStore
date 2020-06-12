import { getManager,UpdateResult } from 'typeorm';
import { Store_usuario } from '../entity/Store_usuario';

export class StoreUsuarioDAO{
    

    async getUsrByUsrAndPasswd(req:any):Promise<Store_usuario| undefined> {
        var filter='';
       
        if(req.login){ 
            filter += " usr.login = '"+req.login+"'"; 
            if(req.passwd){ 
                filter += " AND usr.passwd = '"+req.passwd+"'"; 
            }else{
                return await new Promise<Store_usuario>((_, reject) => reject({error:'There is not password to filter'}));
                
            }
        }
        else{
            return await new Promise<Store_usuario>((_, reject) => reject({error:'There is not user to filter'}))
                
        }
       return await getManager().getRepository(Store_usuario).createQueryBuilder('usr')
            .select('usr.id_usr')
            .addSelect('usr.login').addSelect('usr.nombre').addSelect('usr.correo')
            .addSelect('usr.telefono').addSelect('usr.direccion').addSelect('usr.intentos')
            .addSelect('usr.bloqueado').addSelect('usr.activo').addSelect('usr.id_perfil').addSelect('usr.id_sucursal')
            
        //  .leftJoinAndSelect("usr.suc","suc")

            .where(filter).getOne().then((resultado)=> resultado)!;

       // return user;
    }
}