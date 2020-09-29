
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { ProdPedidos } from '../objects/ProdPedidos';

import config from 'src/app/config/global.json';
@Injectable({
    providedIn: 'root'
})
export class ProductsPedidoService{
    baseURL = config.service.baseURL//+":"+config.service.port;
    
    constructor(private http: HttpClient) { }

    getProductsByPedido = (folio:String) => {
        return this.http.post(`${this.baseURL}/getProductsByPedido`,{'folio':folio})
            .pipe(
                map(
                    (res:ProdPedidos[]) => res
                ),
                catchError(this.handleError)
            );
      }
      private handleError(error: HttpErrorResponse) {
        console.log(error)
        return throwError('Error! something went wrong.');
      }
}