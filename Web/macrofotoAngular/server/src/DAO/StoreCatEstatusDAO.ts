import { getManager,UpdateResult } from 'typeorm';
import { Store_usuario } from '../entity/Store_usuario';
import { Store_cat_estatus } from '../entity/Store_cat_estatus';

export class StoreCatEstatusDAO{
    

    async getCatStatus():Promise<Store_cat_estatus[]| undefined> {
        
        return await getManager().getRepository(Store_cat_estatus).
        createQueryBuilder('cat').
        getMany().then(res=>res);
    }
}