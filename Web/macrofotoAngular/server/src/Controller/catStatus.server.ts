import { StorePedidoDAO } from '../DAO/StorePedidoDAO';
import {  Connection } from 'typeorm';
import { StoreCatEstatusDAO } from '../DAO/StoreCatEstatusDAO';

export class CatStatusServer{
   
    resourcesCatStatus = (app:any,conn:Connection):void=>{
        let statusDAO = new StoreCatEstatusDAO();

         app.get('/getCatStatus',async(req:any, res:any)=> {
            
            var listPedido =await statusDAO.getCatStatus().catch(function(error) {
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