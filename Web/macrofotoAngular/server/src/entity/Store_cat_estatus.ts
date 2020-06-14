import {Entity,Column, PrimaryGeneratedColumn,PrimaryColumn,BaseEntity, JoinColumn, ManyToOne} from "typeorm"
import { Store_pedido } from './Store_pedido';

@Entity()
export class Store_cat_estatus{
    @PrimaryGeneratedColumn()
 //   @ManyToOne(type =>Store_pedido, pedido => pedido.id_estatus)
    id_estatus:number ;

    @Column({
        type:'varchar',
        length:'20',
        nullable:false
    })
    estatus:string | null;
}