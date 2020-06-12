import { Store_usuario } from '../entity/Store_usuario';
import { Store_sucursal } from '../entity/Store_sucursal';

import { ResponseDTO } from '../DTO/ResponseDTO';

export default class UserSessionDTO extends Store_usuario{

    
    sucursalUsr:Store_sucursal | undefined;
    response:ResponseDTO;

    constructor() {
        super();
        this.response = new ResponseDTO();
        this.sucursalUsr = new Store_sucursal();
    }
}
