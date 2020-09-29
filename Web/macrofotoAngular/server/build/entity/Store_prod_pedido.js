"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Store_prod_pedido = void 0;
const tslib_1 = require("tslib");
const typeorm_1 = require("typeorm");
let Store_prod_pedido = class Store_prod_pedido {
};
tslib_1.__decorate([
    typeorm_1.PrimaryGeneratedColumn(),
    tslib_1.__metadata("design:type", Number)
], Store_prod_pedido.prototype, "id_prod_pedido", void 0);
tslib_1.__decorate([
    typeorm_1.Column({ type: 'varchar' }),
    tslib_1.__metadata("design:type", String)
], Store_prod_pedido.prototype, "bar_code", void 0);
tslib_1.__decorate([
    typeorm_1.Column({ type: 'varchar' }),
    tslib_1.__metadata("design:type", String)
], Store_prod_pedido.prototype, "descripcion", void 0);
tslib_1.__decorate([
    typeorm_1.Column({ type: 'int' }),
    tslib_1.__metadata("design:type", Number)
], Store_prod_pedido.prototype, "cantidad", void 0);
tslib_1.__decorate([
    typeorm_1.Column({ type: 'decimal', precision: 10, scale: 2 }),
    tslib_1.__metadata("design:type", Number)
], Store_prod_pedido.prototype, "costo_unitario", void 0);
tslib_1.__decorate([
    typeorm_1.Column({ type: 'decimal', precision: 10, scale: 2 }),
    tslib_1.__metadata("design:type", Number)
], Store_prod_pedido.prototype, "costo_total", void 0);
tslib_1.__decorate([
    typeorm_1.Column({ type: 'varchar' }),
    tslib_1.__metadata("design:type", String)
], Store_prod_pedido.prototype, "estatus", void 0);
tslib_1.__decorate([
    typeorm_1.Column({ type: 'varchar' }),
    tslib_1.__metadata("design:type", String)
], Store_prod_pedido.prototype, "folio", void 0);
Store_prod_pedido = tslib_1.__decorate([
    typeorm_1.Entity()
], Store_prod_pedido);
exports.Store_prod_pedido = Store_prod_pedido;
