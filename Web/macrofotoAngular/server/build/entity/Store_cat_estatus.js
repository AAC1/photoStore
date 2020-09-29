"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Store_cat_estatus = void 0;
const tslib_1 = require("tslib");
const typeorm_1 = require("typeorm");
let Store_cat_estatus = class Store_cat_estatus {
};
tslib_1.__decorate([
    typeorm_1.PrimaryGeneratedColumn(),
    tslib_1.__metadata("design:type", Number)
], Store_cat_estatus.prototype, "id_estatus", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'varchar',
        length: '20',
        nullable: false
    }),
    tslib_1.__metadata("design:type", Object)
], Store_cat_estatus.prototype, "estatus", void 0);
Store_cat_estatus = tslib_1.__decorate([
    typeorm_1.Entity()
], Store_cat_estatus);
exports.Store_cat_estatus = Store_cat_estatus;
