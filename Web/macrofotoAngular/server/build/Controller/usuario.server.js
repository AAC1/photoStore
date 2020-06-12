"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.UsuarioServer = void 0;
const StoreUsuarioDAO_1 = require("../DAO/StoreUsuarioDAO");
class UsuarioServer {
    constructor() {
        this.resourcesUsuario = (app, conn) => {
            const usuarioDAO = new StoreUsuarioDAO_1.StoreUsuarioDAO();
            var listPedido = [];
            app.get('/login', (req, res) => {
                console.log(req);
                listPedido = usuarioDAO.getUsrByFilter(req);
                res.send(listPedido);
            });
        };
    }
}
exports.UsuarioServer = UsuarioServer;
