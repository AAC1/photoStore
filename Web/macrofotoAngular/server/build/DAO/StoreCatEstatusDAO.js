"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.StoreCatEstatusDAO = void 0;
const tslib_1 = require("tslib");
const typeorm_1 = require("typeorm");
const Store_cat_estatus_1 = require("../entity/Store_cat_estatus");
class StoreCatEstatusDAO {
    getCatStatus() {
        return tslib_1.__awaiter(this, void 0, void 0, function* () {
            return yield typeorm_1.getManager().getRepository(Store_cat_estatus_1.Store_cat_estatus).
                createQueryBuilder('cat').
                getMany().then(res => res);
        });
    }
}
exports.StoreCatEstatusDAO = StoreCatEstatusDAO;
