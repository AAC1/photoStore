"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.PedidosServer = void 0;
const tslib_1 = require("tslib");
const StorePedidoDAO_1 = require("../DAO/StorePedidoDAO");
class PedidosServer {
    constructor() {
        this.resourcesPedidos = (app, conn) => {
            let pedidoDAO = new StorePedidoDAO_1.StorePedidoDAO();
            app.post('/getPedidos', (req, res) => tslib_1.__awaiter(this, void 0, void 0, function* () {
                var listPedido = yield pedidoDAO.getPedido(req.body).catch(function (error) {
                    console.log('Caught!', error);
                });
                console.log(listPedido);
                if (!listPedido)
                    res.send([]);
                else
                    res.send(listPedido);
            }));
        };
    }
}
exports.PedidosServer = PedidosServer;
