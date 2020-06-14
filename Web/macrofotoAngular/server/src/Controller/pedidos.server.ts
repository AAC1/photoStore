import { StorePedidoDAO } from '../DAO/StorePedidoDAO';
import {  Connection } from 'typeorm';

export class PedidosServer{
   
    resourcesPedidos = (app:any,conn:Connection):void=>{
        let pedidoDAO = new StorePedidoDAO();

         app.post('/getPedidos',async(req:any, res:any)=> {
            
            var listPedido =await pedidoDAO.getPedido(req.body).catch(function(error) {
                console.log('Caught!', error);
            });
            console.log(listPedido)
            if(!listPedido)
                res.send([]);
            else
                res.send(listPedido);
        });
    
    }
}
