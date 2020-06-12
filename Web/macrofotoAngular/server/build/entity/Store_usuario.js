"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Store_usuario = void 0;
const tslib_1 = require("tslib");
const typeorm_1 = require("typeorm");
class Store_usuario {
}
tslib_1.__decorate([
    typeorm_1.PrimaryGeneratedColumn(),
    typeorm_1.Column({
        type: 'int',
        nullable: false
    }),
    tslib_1.__metadata("design:type", Object)
], Store_usuario.prototype, "id_usr", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'varchar',
        length: '11',
        nullable: false
    }),
    tslib_1.__metadata("design:type", Object)
], Store_usuario.prototype, "login", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'varchar',
        length: '300',
        nullable: false
    }),
    tslib_1.__metadata("design:type", Object)
], Store_usuario.prototype, "passwd", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'varchar',
        length: '150',
        nullable: false
    }),
    tslib_1.__metadata("design:type", Object)
], Store_usuario.prototype, "nombre", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'varchar',
        length: '150',
        nullable: true
    }),
    tslib_1.__metadata("design:type", Object)
], Store_usuario.prototype, "correo", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'varchar',
        length: '10',
        nullable: true
    }),
    tslib_1.__metadata("design:type", Object)
], Store_usuario.prototype, "telefono", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'varchar',
        length: '200',
        nullable: true
    }),
    tslib_1.__metadata("design:type", Object)
], Store_usuario.prototype, "direccion", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'int',
        nullable: false
    }),
    tslib_1.__metadata("design:type", Object)
], Store_usuario.prototype, "intentos", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'int',
        nullable: true
    }),
    tslib_1.__metadata("design:type", Object)
], Store_usuario.prototype, "bloqueado", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'int',
        nullable: true
    }),
    tslib_1.__metadata("design:type", Object)
], Store_usuario.prototype, "activo", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'int',
        nullable: true
    }),
    tslib_1.__metadata("design:type", Object)
], Store_usuario.prototype, "id_perfil", void 0);
tslib_1.__decorate([
    typeorm_1.Column({
        type: 'int',
        nullable: true
    }),
    tslib_1.__metadata("design:type", Object)
], Store_usuario.prototype, "id_sucursal", void 0);
exports.Store_usuario = Store_usuario;
