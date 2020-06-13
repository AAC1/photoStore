import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import { Pedido } from 'src/app/objects/Pedidos';
import * as config from 'config'
@Injectable({
    providedIn: 'root'
})
export class PedidosService{
    baseURL = config.get('service.baseURL')+":"+config.get('service.port');
    pedido: Pedido[];
    constructor(private http: HttpClient) { }

    getPedido(jsonIn: Pedido): Observable<Pedido[]> {
        return this.http.post(`${this.baseURL}/getPedidos`, jsonIn )
          .pipe(map((res:Pedido[]) => {
            this.pedido = res;
            return this.pedido;
          }),
          catchError(this.handleError));
    
      }
      private handleError(error: HttpErrorResponse) {
        console.log(error)
        return throwError('Error! something went wrong.');
      }
}