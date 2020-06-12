import {Entity,Column, PrimaryGeneratedColumn} from "typeorm"

@Entity()
export  class Store_sucursal{

    @PrimaryGeneratedColumn()
    id_sucursal:number ;

    @Column({
        type:'varchar',
        length:'50',
        nullable:false
    })
    sucursal:string | null;

    @Column({
        type:'varchar',
        length:'150',
        nullable:true
    })
    razon_social:string | null;

    @Column({
        type:'varchar',
        length:'200',
        nullable:true
    })
    direccion:string | null;
    
    @Column({
        type:'varchar',
        length:'10',
        nullable:true
    })
    telefono:string | null;
    
    @Column({
        type:'varchar',
        length:'5',
        nullable:false
    })
    prefijo:string | null;
    
}