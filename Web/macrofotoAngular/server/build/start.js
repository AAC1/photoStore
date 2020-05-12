"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.StartServer = void 0;
const express = require("express");
class StartServer {
    constructor() {
        this.app = express();
        this.port = 3000;
    }
    get() { }
}
exports.StartServer = StartServer;
(req, res) => {
    res.send("Hello world!");
};
;
