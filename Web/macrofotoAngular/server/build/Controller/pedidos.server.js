"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.PedidosServer = void 0;
const StorePedidoDAO_1 = require("../DAO/StorePedidoDAO");
class PedidosServer {
    constructor() {
        this.resourcesPedidos = (app, conn) => {
            let pedidoDAO = new StorePedidoDAO_1.StorePedidoDAO();
            app.get('/getPedidos', (req, res) => {
                console.log(req);
                var filter = '';
                var listPedido = pedidoDAO.getPedido(req);
                res.send(listPedido);
            });
        };
    }
}
exports.PedidosServer = PedidosServer;
