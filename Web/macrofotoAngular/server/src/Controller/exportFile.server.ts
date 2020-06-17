import {  Connection } from 'typeorm';

var jasper = require('node-jasper')({
    path: "../lib/jasperreports-5.6.1/",
    reports: {
        hw: {
            jasper: '../reports/reportePedidos.jasper'
        },

        barCode: {
            jasper: '../reports/TblBarCode.jasper'
        }
    },
    drivers: {
        mysql: {
            path: '../../util/mysql/mysql-connector-java-5.1.45.jar',
            class: 'com.mysql.jdbc.Driver',
            type: 'mysql'
        }
    },
    conns: {
        dbserver1: {
            host: 'localhost',
            port: 3306,
            dbname: 'macrofoto',
            user: 'macrofotoUsr',
            pass: 'M4cr0f0t01',
            driver: 'mysql',
            jdbc:'jdbc:mysql://localhost:3306/macrofoto?user=macrofotoUsr&password=M4cr0f0t01&useSSL=false'
        }
    },
    defaultConn: 'dbserver1'
});

export class ExportFileServer{

    resourcesExportFile = (app:any,conn:Connection):void=>{
        
         app.post('/reportePedidosXLS',async(req:any, res:any)=> {
            var qry = this.generateQry(req.body);
            console.log(qry);
            var report = {
                report: 'barCode',
                data: {
                    titulo: "MACROFOTO DIGITAL S.A DE C.V.",
                   // SUBREPORT_DIR:'../reportes/',
                  //  qry:qry/*,
                 //   secundaryDataset: jasper.toJsonDataSource({
                //        data: ''
               //     },'data')*/
                }
                //,dataset: null//main dataset
            };
           
            //jasper.ready(()=>{
                var xls  = jasper.pdf(report);
                console.log(xls);
                res.set({
                    'Content-type': 'application/json',//vnd.ms-excel
                    'Content-Length': xls.length
                });
                res.send(xls);
          //  });
            console.log("Done!//////////////")
            
            
        });
    }
    private generateQry = (req:any):String=>{
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