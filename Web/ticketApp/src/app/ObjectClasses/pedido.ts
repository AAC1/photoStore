export class Pedido{
    id_pedido: number;
    folio: string;
    cliente: string;
    telefono: number;
    descripcion: string;
    fec_pedido: string;
    fec_entregado: string;
    monto_ant: number;
    monto_total: number;
    id_estatus: number;
    fec_pedidoIni: string;
    fec_pedidoFin: string;

    constructor(
        id_pedido: number,
        folio: string,
        cliente: string,
        telefono: number,
        descripcion: string,
        fec_pedido: string,
        fec_entregado: string,
        monto_ant: number,
        monto_total: number,
        id_estatus: number
    ){}
}