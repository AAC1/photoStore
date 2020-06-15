import { getManager,UpdateResult } from 'typeorm';
import { Store_prod_pedido } from '../entity/Store_prod_pedido';
export class StoreProdPedidoDAO{

    async getProductsByPedido(idPedido:number){
        return await getManager().getRepository(Store_prod_pedido)
        .createQueryBuilder('prodPed')
        .where('prodPed.id_pedido = :idPedido',{idPedido : idPedido}).getMany().then(e=>e);
    }
}