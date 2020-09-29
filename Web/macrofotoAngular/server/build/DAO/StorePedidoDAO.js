"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.StorePedidoDAO = void 0;
const tslib_1 = require("tslib");
const Store_pedido_1 = require("../entity/Store_pedido");
const typeorm_1 = require("typeorm");
class StorePedidoDAO {
    getPedido(req) {
        return tslib_1.__awaiter(this, void 0, void 0, function* () {
            var filter = '';
            if (req.folio) {
                filter += " pedido.folio like '%" + req.folio + "%'";
            }
            else {
                filter += " pedido.folio like '%'";
            }
            if (req.cliente) {
                filter += " AND pedido.folio like '%" + req.cliente + "%'";
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
            return yield typeorm_1.getManager().getRepository(Store_pedido_1.Store_pedido)
                .createQueryBuilder('pedido')
                .leftJoinAndMapOne('pedido.catEstatus', 'Store_cat_estatus', 'cat', 'pedido.id_estatus = cat.id_estatus')
                .where(filter).getMany().then(e => e);
        });
    }
}
exports.StorePedidoDAO = StorePedidoDAO;
