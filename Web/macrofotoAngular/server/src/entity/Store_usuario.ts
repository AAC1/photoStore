import {Entity,Column, PrimaryGeneratedColumn,PrimaryColumn,BaseEntity} from "typeorm"

@Entity()
export class Store_usuario {
    
    @PrimaryGeneratedColumn()
    id_usr:number ;

    @Column({
        type:'varchar',
        length:'11',
        nullable:false
    })
    login:string | null;

    @Column({
        type:'varchar',
        length:'300',
        nullable:false
    })
    passwd: string| null;

    @Column({
        type:'varchar',
        length:'150',
        nullable:false
    })
    nombre:string| null;

    @Column({
        type:'varchar',
        length:'150',
        nullable:true
    })
    correo:string | null;

    @Column({
        type:'varchar',
        length:'10',
        nullable:true
    })
    telefono:string | null;

    @Column({
        type:'varchar',
        length:'200',
        nullable:true
    })
    direccion:string | null;

    @Column({
        type:'int',
        nullable:true
    })
    intentos:number ;

    @Column({
        type:'int',
        nullable:true
    })
    bloqueado:number;

    @Column({
        type:'int',
        nullable:true
    })
    activo:number ;

    @Column({
        type:'int',
        nullable:true
    })
    id_perfil:number ;

    @Column({
        type:'int',
        nullable:true
    })
    id_sucursal:number ;
}