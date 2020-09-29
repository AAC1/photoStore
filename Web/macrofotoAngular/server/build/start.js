"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Start = void 0;
const tslib_1 = require("tslib");
const express = require("express");
const pedidos_server_1 = require("./Controller/pedidos.server");
const typeorm_1 = require("typeorm");
const usuario_server_1 = require("./Controller/usuario.server");
const catStatus_server_1 = require("./Controller/catStatus.server");
const prodPedido_server_1 = require("./Controller/prodPedido.server");
const exportFile_server_1 = require("./Controller/exportFile.server");
class Start {
    constructor(port) {
        this.app = express();
        this.port = 3000;
        this.urlOrigin = "https://bitmaking.000webhostapp.com/";
        this.app = express();
        this.port = port;
        this.initializeModels();
        this.initializeMiddlewares();
        this.initializeControllers();
    }
    initializeModels() {
        return tslib_1.__awaiter(this, void 0, void 0, function* () {
            console.log("Initializing connection...");
            const connection = yield typeorm_1.createConnection();
            if (connection === undefined) {
                console.log("Error connecting to database");
                throw new Error('Error connecting to database');
            }
            connection.synchronize();
            this.connection = connection;
        });
    }
    initializeMiddlewares() {
        this.app.use(express.json());
    }
    initializeControllers() {
        this.app.use(function (req, res, next) {
            res.header("Access-Control-Allow-Origin", "*");
            res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
            res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
            res.setHeader('Access-Control-Allow-Credentials', "true");
            next();
        });
        this.app.get("/", (req, res) => {
            res.send("Hello world!");
        });
        let pedidos = new pedidos_server_1.PedidosServer();
        pedidos.resourcesPedidos(this.app, this.connection);
        let usuario = new usuario_server_1.UsuarioServer();
        usuario.resourcesUsuario(this.app, this.connection);
        let catStatus = new catStatus_server_1.CatStatusServer();
        catStatus.resourcesCatStatus(this.app, this.connection);
        let prodPedido = new prodPedido_server_1.ProdPedidoServer();
        prodPedido.resourcesProductPedido(this.app, this.connection);
        let exportFile = new exportFile_server_1.ExportFileServer();
        exportFile.resourcesExportFile(this.app, this.connection);
        this.listen();
    }
    listen() {
        this.app.listen(this.port, () => {
            console.log(`Server running on port ${this.port}`);
        });
    }
}
exports.Start = Start;
var init = new Start(3000);
