"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.ExportFileServer = void 0;
const tslib_1 = require("tslib");
const StorePedidoDAO_1 = require("../DAO/StorePedidoDAO");
const StoreProdPedidoDAO_1 = require("../DAO/StoreProdPedidoDAO");
class ExportFileServer {
    constructor() {
        this.resourcesExportFile = (app, conn) => {
            let pedidoDAO = new StorePedidoDAO_1.StorePedidoDAO();
            let prodPedidoDAO = new StoreProdPedidoDAO_1.StoreProdPedidoDAO();
            app.post('/reportePedidosXLS', (req, res) => tslib_1.__awaiter(this, void 0, void 0, function* () {
                var listPedido = yield pedidoDAO.getPedido(req.body).catch(function (error) {
                    console.log('Caught!', error);
                });
                if (!listPedido)
                    res.send([]);
                else {
                    for (let i = 0; i < listPedido.length; i++) {
                        const listProds = yield prodPedidoDAO.getProductsByPedido(listPedido[i].folio).catch(function (error) {
                            console.log('Caught!', error);
                        });
                        if (!listProds)
                            listPedido[i].products = [];
                        else
                            listPedido[i].products = listProds;
                    }
                    res.send(listPedido);
                }
            }));
        };
        this._generateQry = (req) => {
            let qry = "SELECT p.folio, p.cliente, p.telefono, p.descripcion, p.fec_pedido,";
            qry += " p.fec_entregado, p.monto_ant, p.monto_total, (IFNULL(p.monto_total,0) - IFNULL(p.monto_ant,0) ) monto_pendiente, ";
            qry += " (select s.estatus from Store_cat_estatus s where s.id_estatus=p.id_estatus) as estatus, p.ticket ";
            qry += " FROM Store_pedido p ";
            qry += "WHERE p.folio like '%";
            if (!req.folio && req.folio !== undefined) {
                qry += req.folio;
            }
            qry += "%'";
            if (req.estatus && req.estatus.length > 0) {
                qry += " AND p.id_estatus = " + req.estatus;
            }
            if (req.fecIni && req.fecIni.length > 0) {
                qry += " AND p.fec_pedido >= '" + req.fecIni + "'";
            }
            if (req.fecFin && req.fecFin.length > 0) {
                qry += " AND p.fec_pedido <= '" + req.fecFin + "'";
            }
            return qry;
        };
    }
}
exports.ExportFileServer = ExportFileServer;
