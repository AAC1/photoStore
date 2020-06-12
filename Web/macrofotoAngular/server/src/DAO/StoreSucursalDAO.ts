import { Store_sucursal } from '../entity/Store_sucursal';
import { getManager,UpdateResult } from 'typeorm';

export class StoreSucursalDAO{

    async getSucById(idSuc:number):Promise<Store_sucursal| undefined> {
        
       return await getManager().getRepository(Store_sucursal)
            .findOne({
                where:{id_sucursal : idSuc}})
            

    }
}
