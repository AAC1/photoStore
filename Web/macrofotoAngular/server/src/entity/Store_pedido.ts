import {Entity,Column, PrimaryGeneratedColumn,BaseEntity, PrimaryColumn, OneToMany, JoinColumn} from "typeorm"
import { Store_cat_estatus } from './Store_cat_estatus';
import { Store_prod_pedido } from './Store_prod_pedido';

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
    monto_ant:number ;

    @Column({
        type:'varchar',
        nullable:true
    })
    monto_total:number ;

 //   @OneToMany (type => Store_cat_estatus, cat => cat.id_estatus)
 //   @JoinColumn()
    @Column({type:'int'})
    id_estatus:number ;

    @Column({
        type:'longblob',
        nullable:true
    })
    ticket:BinaryType | null;

   // @Column()
    catEstatus:Store_cat_estatus;
    products: Store_prod_pedido[];

}