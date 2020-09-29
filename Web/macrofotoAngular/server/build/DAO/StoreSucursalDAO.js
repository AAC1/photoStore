"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.StoreSucursalDAO = void 0;
const tslib_1 = require("tslib");
const Store_sucursal_1 = require("../entity/Store_sucursal");
const typeorm_1 = require("typeorm");
class StoreSucursalDAO {
    getSucById(idSuc) {
        return tslib_1.__awaiter(this, void 0, void 0, function* () {
            return yield typeorm_1.getManager().getRepository(Store_sucursal_1.Store_sucursal)
                .findOne({
                where: { id_sucursal: idSuc }
            });
        });
    }
}
exports.StoreSucursalDAO = StoreSucursalDAO;
