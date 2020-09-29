"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.CatStatusServer = void 0;
const tslib_1 = require("tslib");
const StoreCatEstatusDAO_1 = require("../DAO/StoreCatEstatusDAO");
class CatStatusServer {
    constructor() {
        this.resourcesCatStatus = (app, conn) => {
            let statusDAO = new StoreCatEstatusDAO_1.StoreCatEstatusDAO();
            app.get('/getCatStatus', (req, res) => tslib_1.__awaiter(this, void 0, void 0, function* () {
                var listPedido = yield statusDAO.getCatStatus().catch(function (error) {
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
exports.CatStatusServer = CatStatusServer;
