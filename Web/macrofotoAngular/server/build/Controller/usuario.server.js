"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.UsuarioServer = void 0;
const tslib_1 = require("tslib");
const StoreUsuarioDAO_1 = require("../DAO/StoreUsuarioDAO");
const sha255 = require("sha256");
const UserSessionDTO_1 = require("../DTO/UserSessionDTO");
const StoreSucursalDAO_1 = require("../DAO/StoreSucursalDAO");
class UsuarioServer {
    constructor() {
        this.resourcesUsuario = (app, conn) => {
            const SALT = "3d777fc8f098c8c18e457560352c4c619296801b65614cb9e49ec005aee55352";
            app.post('/login', (req, res) => tslib_1.__awaiter(this, void 0, void 0, function* () {
                console.log(req);
                res.send(yield this.getUserSession(req.body, SALT));
            }));
        };
        this.getUserSession = (params, SALT) => tslib_1.__awaiter(this, void 0, void 0, function* () {
            const usuarioDAO = new StoreUsuarioDAO_1.StoreUsuarioDAO();
            const sucursalDAO = new StoreSucursalDAO_1.StoreSucursalDAO();
            var usrSessionObj = new UserSessionDTO_1.default();
            if (params && typeof params.passwd !== 'undefined') {
                var passwd = params.passwd + SALT;
                params.passwd = sha255(passwd);
                console.log(params.passwd);
            }
            const usrObj = yield usuarioDAO.getUsrByUsrAndPasswd(params).catch(function (error) {
                console.log('Caught!', error);
            });
            if (usrObj !== undefined) {
                if (usrObj.bloqueado == 1) {
                    usrSessionObj.response.message = "Usuario bloqueado. Contacte al administrador";
                    usrSessionObj.response.status = "ERROR";
                    usrSessionObj.response.code = -1;
                    return usrSessionObj;
                }
                if (usrObj.activo == 0) {
                    usrSessionObj.response.message = "Usuario dado de baja. Contacte al administrador";
                    usrSessionObj.response.status = "ERROR";
                    usrSessionObj.response.code = -1;
                    return usrSessionObj;
                }
                usrSessionObj.id_usr = usrObj.id_usr;
                usrSessionObj.login = usrObj.login;
                usrSessionObj.nombre = usrObj.nombre;
                usrSessionObj.correo = usrObj.correo;
                usrSessionObj.telefono = usrObj.telefono;
                usrSessionObj.direccion = usrObj.direccion;
                usrSessionObj.id_perfil = usrObj.id_perfil;
                usrSessionObj.id_sucursal = usrObj.id_sucursal;
                usrSessionObj.sucursalUsr = yield sucursalDAO.getSucById(usrObj.id_sucursal);
                usrSessionObj.response.status = "OK";
            }
            else {
                usrSessionObj.response.message = "Usuario no encontrado, valide la contrase\u00F1a y el usuario";
                usrSessionObj.response.status = "ERROR";
                usrSessionObj.response.code = -1;
            }
            return usrSessionObj;
        });
    }
}
exports.UsuarioServer = UsuarioServer;
