import { Store_pedido } from '../entity/Store_pedido';
import { getManager,UpdateResult } from 'typeorm';
import { Store_cat_estatus } from '../entity/Store_cat_estatus';

export class StorePedidoDAO{

    async getPedido(req:any):Promise<Store_pedido[]>{
        var filter='';

        if(req.folio){ filter += " pedido.folio like '%"+req.folio+"%'"; }
        else{filter +=" pedido.folio like '%'"}

        if(req.cliente){ filter += " AND pedido.folio like '%"+req.cliente+"%'"; }
        
        if(req.estatus){ filter += " AND pedido.id_estatus = "+req.estatus; }
        
        if(req.fecIni){ 
            filter += " AND pedido.fec_pedido >= '"+req.fecIni+"'"; 
            
        }
        if(req.fecFin){ 
            filter += " AND pedido.fec_pedido <= '"+req.fecFin+"'"; 
            
        }
      
        return await getManager().getRepository(Store_pedido)
        .createQueryBuilder('pedido')
        .leftJoinAndMapOne('pedido.catEstatus', 'Store_cat_estatus','cat','pedido.id_estatus = cat.id_estatus')
        .where(filter).getMany().then(e=>e);
    }
}

