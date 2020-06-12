"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.StoreUsuarioDAO = void 0;
const typeorm_1 = require("typeorm");
const Store_usuario_1 = require("../entity/Store_usuario");
class StoreUsuarioDAO {
    getUsrByFilter(req) {
        var filter = '';
        var resp;
        if (req.folio) {
            filter += " usr.login = '" + req.login + "'";
            if (req.passwd) {
                filter += " AND usr.passwd like '" + req.passwd + "'";
            }
            else {
                return new Promise((resolve, reject) => reject('Ingrese password'));
            }
        }
        else {
            return new Promise((resolve, reject) => reject('Ingrese usuario'));
        }
        if (req.estatus) {
            filter += " AND usr.id_estatus = " + req.estatus;
        }
        if (req.fecIni) {
            filter += " AND usr.fec_pedido >= '" + req.fecIni + "'";
        }
        if (req.fecFin) {
            filter += " AND usr.fec_pedido <= '" + req.fecFin + "'";
        }
        return typeorm_1.getManager().getRepository(Store_usuario_1.Store_usuario).createQueryBuilder('usr').
            where(filter).getMany();
    }
}
exports.StoreUsuarioDAO = StoreUsuarioDAO;
