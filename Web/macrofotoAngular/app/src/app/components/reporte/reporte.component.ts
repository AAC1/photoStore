import { Component, OnInit } from '@angular/core';
import { Pedido } from 'src/app/objects/pedidos';
import { PedidosService } from 'src/app/services/pedidos.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Component({
  selector: 'app-reporte',
  templateUrl: './reporte.component.html',
  styleUrls: ['./reporte.component.css']
})
@Injectable()
export class ReporteComponent implements OnInit {

  constructor(private pedidosService: PedidosService) { }
  ngOnInit() {
    this.getPedido();
  }
  
  pedido: Pedido;
  listPedidos: Pedido[];
  error = '';
  objPagination: any = {};
  
  getPedido(): void {
    this.pedidosService.getPedido(this.pedido).subscribe(
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
