
export class PedidosServer{
   
    resourcesPedidos = (app:any):void=>{

        app.get('/getPedidos',(req:any, res:any)=> {
            console.log(req);
            
            res.send('Hello,  Reporte works!');
        });
    
    }
}
