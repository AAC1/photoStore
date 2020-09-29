"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Store_pedido = void 0;
const tslib_1 = require("tslib");
const typeorm_1 = require("typeorm");
let Store_pedido = class Store_pedido extends typeorm_1.BaseEntity {
};
tslib_1.__decorate([
    typeorm_1.PrimaryColumn('uuid'),
    tslib_1.__metadata("design:type", String)
], Store_pedido.prototype, "folio", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'varchar',
        length: '100',
        nullable: true
    }),
    tslib_1.__metadata("design:type", Object)
], Store_pedido.prototype, "cliente", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'varchar',
        length: '10',
        nullable: true
    }),
    tslib_1.__metadata("design:type", Object)
], Store_pedido.prototype, "telefono", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'varchar',
        length: '100',
        nullable: true
    }),
    tslib_1.__metadata("design:type", Object)
], Store_pedido.prototype, "descripcion", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'datetime',
        nullable: true
    }),
    tslib_1.__metadata("design:type", Object)
], Store_pedido.prototype, "fec_pedido", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'datetime',
        nullable: true
    }),
    tslib_1.__metadata("design:type", Object)
], Store_pedido.prototype, "fec_entregado", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'decimal', precision: 10, scale: 2,
        nullable: true
    }),
    tslib_1.__metadata("design:type", Number)
], Store_pedido.prototype, "monto_ant", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'varchar', precision: 10, scale: 2,
        nullable: true
    }),
    tslib_1.__metadata("design:type", Number)
], Store_pedido.prototype, "monto_total", void 0);
tslib_1.__decorate([
    typeorm_1.Column({ type: 'int' }),
    tslib_1.__metadata("design:type", Number)
], Store_pedido.prototype, "id_estatus", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'longblob',
        nullable: true
    }),
    tslib_1.__metadata("design:type", Object)
], Store_pedido.prototype, "ticket", void 0);
Store_pedido = tslib_1.__decorate([
    typeorm_1.Entity()
], Store_pedido);
exports.Store_pedido = Store_pedido;
