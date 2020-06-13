import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import config from 'src/app/config/global.json';

@Injectable({
    providedIn: 'root'
})
export class UsuarioService{
    baseUrl = config.service.baseURL+":"+config.service.port;
    usrSession: any;
    constructor(private http: HttpClient) { }

    login = async (jsonIn: any) => {
        return await this.http.post(`${this.baseUrl}/login`,  jsonIn )
          .pipe(map((res) => {
            this.usrSession = res;
            return this.usrSession;
          }),
          catchError(this.handleError));
    
      }
      private handleError(error: HttpErrorResponse) {
        console.log(error)
        return throwError('Error! something went wrong.');
      }
}