"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.StoreProdPedidoDAO = void 0;
const tslib_1 = require("tslib");
const typeorm_1 = require("typeorm");
const Store_prod_pedido_1 = require("../entity/Store_prod_pedido");
class StoreProdPedidoDAO {
    getProductsByPedido(folio) {
        return tslib_1.__awaiter(this, void 0, void 0, function* () {
            return yield typeorm_1.getManager().getRepository(Store_prod_pedido_1.Store_prod_pedido)
                .createQueryBuilder('prodPed')
                .where('prodPed.folio = :folio', { folio: folio }).getMany().then(e => e);
        });
    }
}
exports.StoreProdPedidoDAO = StoreProdPedidoDAO;
