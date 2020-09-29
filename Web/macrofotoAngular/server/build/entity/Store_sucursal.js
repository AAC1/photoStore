"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Store_sucursal = void 0;
const tslib_1 = require("tslib");
const typeorm_1 = require("typeorm");
let Store_sucursal = class Store_sucursal {
};
tslib_1.__decorate([
    typeorm_1.PrimaryGeneratedColumn(),
    tslib_1.__metadata("design:type", Number)
], Store_sucursal.prototype, "id_sucursal", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'varchar',
        length: '50',
        nullable: false
    }),
    tslib_1.__metadata("design:type", Object)
], Store_sucursal.prototype, "sucursal", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'varchar',
        length: '150',
        nullable: true
    }),
    tslib_1.__metadata("design:type", Object)
], Store_sucursal.prototype, "razon_social", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'varchar',
        length: '200',
        nullable: true
    }),
    tslib_1.__metadata("design:type", Object)
], Store_sucursal.prototype, "direccion", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'varchar',
        length: '10',
        nullable: true
    }),
    tslib_1.__metadata("design:type", Object)
], Store_sucursal.prototype, "telefono", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'varchar',
        length: '5',
        nullable: false
    }),
    tslib_1.__metadata("design:type", Object)
], Store_sucursal.prototype, "prefijo", void 0);
Store_sucursal = tslib_1.__decorate([
    typeorm_1.Entity()
], Store_sucursal);
exports.Store_sucursal = Store_sucursal;
