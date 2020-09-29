"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.ProdPedidoServer = void 0;
const tslib_1 = require("tslib");
const StoreProdPedidoDAO_1 = require("../DAO/StoreProdPedidoDAO");
class ProdPedidoServer {
    constructor() {
        this.resourcesProductPedido = (app, conn) => {
            let prodPedidoDAO = new StoreProdPedidoDAO_1.StoreProdPedidoDAO();
            app.post('/getProductsByPedido', (req, res) => tslib_1.__awaiter(this, void 0, void 0, function* () {
                var listProds = yield prodPedidoDAO.getProductsByPedido(req.body.folio).catch(function (error) {
                    console.log('Caught!', error);
                });
                if (!listProds)
                    res.send([]);
                else
                    res.send(listProds);
            }));
        };
    }
}
exports.ProdPedidoServer = ProdPedidoServer;
