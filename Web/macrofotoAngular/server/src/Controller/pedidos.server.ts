import { StorePedidoDAO } from '../DAO/StorePedidoDAO';
import {  Connection } from 'typeorm';

export class PedidosServer{
   
    resourcesPedidos = (app:any,conn:Connection):void=>{
        let pedidoDAO = new StorePedidoDAO();

        app.get('/getPedidos',(req:any, res:any)=> {
            
            var listPedido =pedidoDAO.getPedido(req.query);
            res.send(listPedido);
        });
    
    }
}
