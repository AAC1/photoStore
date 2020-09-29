"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const Store_usuario_1 = require("../entity/Store_usuario");
const Store_sucursal_1 = require("../entity/Store_sucursal");
const ResponseDTO_1 = require("../DTO/ResponseDTO");
class UserSessionDTO extends Store_usuario_1.Store_usuario {
    constructor() {
        super();
        this.response = new ResponseDTO_1.ResponseDTO();
        this.sucursalUsr = new Store_sucursal_1.Store_sucursal();
    }
}
exports.default = UserSessionDTO;
