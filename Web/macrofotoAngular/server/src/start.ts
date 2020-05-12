import * as express from "express";
import {PedidosServer} from "./resources/pedidos.server";


let app = express();
const port = 3000;

app.get("/", (req: any, res: any) => {
    res.send("Hello world!");
});
let pedidos = new PedidosServer;

pedidos.resourcesPedidos(app);

app.listen(port, () => {
    // tslint:disable-next-line:no-console
    console.log(`server started at http://localhost:${port}`);
});






/*
module.exports = function(app){
    app.get('/getPedidos', function (req, res) {+
        console.log(req);

        res.send('Hello,  Reporte works!');
    });

}
*/