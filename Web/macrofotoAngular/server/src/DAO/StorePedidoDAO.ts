import { Store_pedido } from '../entity/Store_pedido';
import { getManager,UpdateResult } from 'typeorm';

export class StorePedidoDAO{

    getPedido(req:any):Promise<Store_pedido[]>{
        var filter='';

        if(req.folio){ filter += " pedido.folio like '"+req.folio+'%'; }
        else{filter =" pedido.folio like '%'"}

        if(req.cliente){ filter += " AND pedido.folio like '"+req.cliente+"%'"; }
        
        if(req.estatus){ filter += " AND pedido.id_estatus = "+req.estatus; }
        
        if(req.fecIni){ 
            filter += " AND pedido.fec_pedido >= '"+req.fecIni+"'"; 
            
        }
        if(req.fecFin){ 
            filter += " AND pedido.fec_pedido <= '"+req.fecFin+"'"; 
            
        }
        
        return getManager().getRepository(Store_pedido).createQueryBuilder('pedido').
        where(filter).getMany();
    }
}

