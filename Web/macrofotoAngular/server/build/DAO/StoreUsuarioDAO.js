"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.StoreUsuarioDAO = void 0;
const tslib_1 = require("tslib");
const typeorm_1 = require("typeorm");
const Store_usuario_1 = require("../entity/Store_usuario");
class StoreUsuarioDAO {
    getUsrByUsrAndPasswd(req) {
        return tslib_1.__awaiter(this, void 0, void 0, function* () {
            var filter = '';
            if (req.login) {
                filter += " usr.login = '" + req.login + "'";
                if (req.passwd) {
                    filter += " AND usr.passwd = '" + req.passwd + "'";
                }
                else {
                    return yield new Promise((_, reject) => reject({ error: 'There is not password to filter' }));
                }
            }
            else {
                return yield new Promise((_, reject) => reject({ error: 'There is not user to filter' }));
            }
            return yield typeorm_1.getManager().getRepository(Store_usuario_1.Store_usuario).createQueryBuilder('usr')
                .select('usr.id_usr')
                .addSelect('usr.login').addSelect('usr.nombre').addSelect('usr.correo')
                .addSelect('usr.telefono').addSelect('usr.direccion').addSelect('usr.intentos')
                .addSelect('usr.bloqueado').addSelect('usr.activo').addSelect('usr.id_perfil').addSelect('usr.id_sucursal')
                .where(filter).getOne().then((resultado) => resultado);
        });
    }
}
exports.StoreUsuarioDAO = StoreUsuarioDAO;
