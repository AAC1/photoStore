"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.StorePedidoDAO = void 0;
const Store_pedido_1 = require("../entity/Store_pedido");
const typeorm_1 = require("typeorm");
class StorePedidoDAO {
    getPedido(req) {
        var filter = '';
        if (req.folio) {
            filter += " pedido.folio like '" + req.folio + '%';
        }
        else {
            filter = " pedido.folio like '%'";
        }
        if (req.cliente) {
            filter += " AND pedido.folio like '" + req.cliente + "%'";
        }
        if (req.estatus) {
            filter += " AND pedido.id_estatus = " + req.estatus;
        }
        if (req.fecIni) {
            filter += " AND pedido.fec_pedido >= '" + req.fecIni + "'";
        }
        if (req.fecFin) {
            filter += " AND pedido.fec_pedido <= '" + req.fecFin + "'";
        }
        return typeorm_1.getManager().getRepository(Store_pedido_1.Store_pedido).createQueryBuilder('pedido').
            where(filter).getMany();
    }
}
exports.StorePedidoDAO = StorePedidoDAO;
