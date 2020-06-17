import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import { Pedido } from 'src/app/objects/Pedidos';
import config from 'src/app/config/global.json';
import { StoreCatStatus } from '../objects/StoreCatStatus';

@Injectable({
    providedIn: 'root'
})
export class ExportFileService{
    baseURL = config.service.baseURL+":"+config.service.port;
    
    constructor(private http: HttpClient) { }

    getCatStatus(json:any) {//Observable<StoreCatStatus[]>
        return this.http.post(`${this.baseURL}/reportePedidosXLS`, json)
            .pipe(
                map(
                    (res:StoreCatStatus[]) => res
                ),
                catchError(this.handleError)
            );
    
      }
      private handleError(error: HttpErrorResponse) {
        console.log(error)
        return throwError('Error! something went wrong.');
      }
}