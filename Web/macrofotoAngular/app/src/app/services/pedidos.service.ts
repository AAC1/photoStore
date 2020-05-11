import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import { Pedido } from 'src/app/objects/pedidos';

@Injectable({
    providedIn: 'root'
})
export class PedidosService{
    baseUrl = 'http://localhost:3000/';
    pedido: Pedido[];
    constructor(private http: HttpClient) { }

    getPedido(jsonIn: Pedido): Observable<Pedido[]> {
        return this.http.post(`${this.baseUrl}/ConsultaPedidos`, { data: jsonIn })
          .pipe(map((res) => {
            this.pedido = res['data'];
            return this.pedido;
          }),
          catchError(this.handleError));
    
      }
      private handleError(error: HttpErrorResponse) {
        console.log(error)
        return throwError('Error! something went wrong.');
      }
}