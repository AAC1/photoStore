import { getManager,UpdateResult } from 'typeorm';
import { Store_prod_pedido } from '../entity/Store_prod_pedido';
export class StoreProdPedidoDAO{

    async getProductsByPedido(folio:String){
        return await getManager().getRepository(Store_prod_pedido)
        .createQueryBuilder('prodPed')
        .where('prodPed.folio = :folio',{folio : folio}).getMany().then(e=>e);
    }
}