import { Component, OnInit } from '@angular/core';
import * as _angular_ from 'angular';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Pedido } from 'src/app/ObjectClasses/pedido';
import { Observable, throwError } from 'rxjs';
import { PedidoService } from 'src/app/services/pedido.service';

declare global {
  var angular: typeof _angular_;
}

@Component({
  selector: 'app-consult-pedido',
  templateUrl: './app.consultaPedido.html',
  styleUrls: ['./app.consultaPedido.css'],
  
})
@Injectable()
export class ConsultaPedidoComponent implements OnInit{
  constructor(private pedidoService: PedidoService) { }
  pedido: Pedido;
  listPedidos: Pedido[];
  error = '';
  objPagination: any = {};

  ngOnInit() {
   this.getPedido();
  }

  getPedido(): void {
    this.pedidoService.getPedido(this.pedido).subscribe(
      (res: Pedido[]) => {
        this.listPedidos = res;
        this.objPagination={
          itemsPerPage: 5,
          currentPage: 1,
          totalItems: this.listPedidos.length
        }
      },
      (err) => {
        this.error = err;
      }
    );
  }

  pageChange(event){
    this.objPagination.currentPage = event;
  }
}
