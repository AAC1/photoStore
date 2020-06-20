import {  Connection } from 'typeorm';
import { StorePedidoDAO } from '../DAO/StorePedidoDAO';
import { StoreProdPedidoDAO } from '../DAO/StoreProdPedidoDAO';

export class ExportFileServer{

    resourcesExportFile = (app:any,conn:Connection):void=>{
        let pedidoDAO = new StorePedidoDAO();
        let prodPedidoDAO = new StoreProdPedidoDAO();
        
         app.post('/reportePedidosXLS',async(req:any, res:any)=> {

            var listPedido =await pedidoDAO.getPedido(req.body).catch(function(error) {
                console.log('Caught!', error);
            });
            
            if(!listPedido) res.send([]);
            else{
               // listPedido.forEach(async (el)=>{
                for(let i=0; i<listPedido.length;i++){
                    const listProds =await prodPedidoDAO.getProductsByPedido(listPedido[i].id_pedido).catch(function(error) {
                        console.log('Caught!', error);
                    });
                 //   console.log(listProds);
                    if(!listProds)listPedido[i].products =[];
                    else listPedido[i].products =listProds;
                }
                res.send(listPedido);
            }
            
        });
    }
    private _generateQry = (req:any):String=>{
        let qry ="SELECT p.id_pedido, p.folio, p.cliente, p.telefono, p.descripcion, p.fec_pedido,"
        qry +=" p.fec_entregado, p.monto_ant, p.monto_total, (IFNULL(p.monto_total,0) - IFNULL(p.monto_ant,0) ) monto_pendiente, ";
		qry +=" (select s.estatus from Store_cat_estatus s where s.id_estatus=p.id_estatus) as estatus, p.ticket ";
		qry +=" FROM Store_pedido p ";
        qry +="WHERE p.folio like '%";
        if(!req.folio && req.folio !== undefined ){
            qry +=req.folio;
        }
        qry +="%'";
        if(req.estatus && req.estatus.length >0){ qry += " AND p.id_estatus = "+req.estatus; }
        
        if(req.fecIni && req.fecIni.length >0){ 
            qry += " AND p.fec_pedido >= '"+req.fecIni+"'"; 
            
        }
        if(req.fecFin && req.fecFin.length >0){ 
            qry += " AND p.fec_pedido <= '"+req.fecFin+"'"; 
            
        }

        return qry;
    }
}