"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const tslib_1 = require("tslib");
const express = require("express");
const pedidos_server_1 = require("./Controller/pedidos.server");
const typeorm_1 = require("typeorm");
const usuario_server_1 = require("./Controller/usuario.server");
class Start {
    constructor(controllers, port) {
        this.app = express();
        this.port = 3000;
        this.app = express();
        this.port = port;
        this.initializeModels();
        this.initializeMiddlewares();
        this.initializeControllers(controllers);
    }
    initializeModels() {
        return tslib_1.__awaiter(this, void 0, void 0, function* () {
            const connection = yield typeorm_1.createConnection();
            if (connection === undefined) {
                throw new Error('Error connecting to database');
            }
            connection.synchronize();
            this.connection = connection;
        });
    }
    initializeMiddlewares() {
        this.app.use(express.json());
    }
    initializeControllers(controllers) {
        this.app.get("/", (req, res) => {
            res.send("Hello world!");
        });
        let pedidos = new pedidos_server_1.PedidosServer();
        pedidos.resourcesPedidos(this.app, this.connection);
        let usuario = new usuario_server_1.UsuarioServer();
        usuario.resourcesUsuario(this.app, this.connection);
    }
    listen() {
        this.app.listen(this.port, () => {
            console.log(`Server running on port ${this.port}`);
        });
    }
}
exports.default = Start;
