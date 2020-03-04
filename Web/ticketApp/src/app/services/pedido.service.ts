import { Injectable } from '@angular/core';
import { Pedido } from 'src/app/ObjectClasses/pedido';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PedidoService {
  baseUrl = 'http://localhost/photo-store/src/php';
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
