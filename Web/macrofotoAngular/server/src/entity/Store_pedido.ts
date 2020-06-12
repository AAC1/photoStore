import {Entity,Column, PrimaryGeneratedColumn,BaseEntity, PrimaryColumn} from "typeorm"

@Entity()
export class Store_pedido extends BaseEntity {
    @PrimaryGeneratedColumn()
    id_pedido: number ;

    @Column({
        type:'varchar',
        length:'100',
        nullable:true
    })
    folio: string ;

    @Column({
        type:'varchar',
        length:'100',
        nullable:true
    })
    cliente:string | null;

    @Column({
        type:'varchar',
        length:'10',
        nullable:true
    })
    telefono:string | null;

    @Column({
        type:'varchar',
        length:'100',
        nullable:true
    })
    descripcion:string | null;

    @Column({
        type:'datetime',
        nullable:true
    })
    fec_pedido:Date | null;

    @Column({
        type:'datetime',
        nullable:true
    })
    fec_entregado:Date | null;

    @Column({
        type:'decimal',
        nullable:true
    })
    monto_ant:number | null;

    @Column({
        type:'varchar',
        nullable:true
    })
    monto_total:number | null;

    @Column({
        type:'int',
        nullable:true
    })
    id_estatus:number | null;

    @Column({
        type:'longblob',
        nullable:true
    })
    ticket:BinaryType | null;

    
}