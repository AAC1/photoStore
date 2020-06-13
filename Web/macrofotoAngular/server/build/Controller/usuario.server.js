"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.UsuarioServer = void 0;
const StoreUsuarioDAO_1 = require("../DAO/StoreUsuarioDAO");
class UsuarioServer {
    constructor() {
        this.resourcesUsuario = (app, conn) => {
            const usuarioDAO = new StoreUsuarioDAO_1.StoreUsuarioDAO();
            var listPedido = [];
            app.post('/login', (req, res) => {

                console.log("request:");
                console.log(req);

                listPedido = usuarioDAO.getUsrByFilter(req.data);
                res.send(listPedido);
            });
        };
    }
}
exports.UsuarioServer = UsuarioServer;
