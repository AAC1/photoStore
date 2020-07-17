import {  Connection } from 'typeorm';
import { Store_prod_pedido } from '../entity/Store_prod_pedido';
import { StoreProdPedidoDAO } from '../DAO/StoreProdPedidoDAO';

export class ProdPedidoServer{
    resourcesProductPedido = (app:any,conn:Connection):void=>{
        let prodPedidoDAO = new StoreProdPedidoDAO();

         app.post('/getProductsByPedido',async(req:any, res:any)=> {
            
            var listProds =await prodPedidoDAO.getProductsByPedido(req.body.folio).catch(function(error) {
                console.log('Caught!', error);
            });
            
            if(!listProds)
                res.send([]);
            else
                res.send(listProds);
        });
    
    }
}