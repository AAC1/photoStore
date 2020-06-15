import { Entity, PrimaryGeneratedColumn, Column } from 'typeorm';

@Entity()
export class Store_prod_pedido{

    @PrimaryGeneratedColumn()
    id_prod_pedido:number;

    @Column({type:'varchar'})
    bar_code:string;

    @Column({type:'varchar'})
    descripcion: string;

    @Column({type:'int'})
    cantidad:number;

    @Column({type:'decimal'})
    costo_unitario:number;

    @Column({type:'decimal'})
    costo_total:number;

    @Column({type:'varchar'})
    estatus:string;

    @Column({type:'int'})
    id_pedido:number;
}