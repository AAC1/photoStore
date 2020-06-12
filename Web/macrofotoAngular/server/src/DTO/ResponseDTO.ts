
export class ResponseDTO{
    message:string;
    code:number;
    value:string;
    status:string;

    constructor(){
        this.message ='';
        this.code = 0;
        this.value = '';
        this.status ='';
    }
}