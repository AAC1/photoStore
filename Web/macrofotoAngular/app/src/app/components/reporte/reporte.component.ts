import { Component, OnInit } from '@angular/core';
import { Pedido } from 'src/app/objects/Pedidos';
import { PedidosService } from 'src/app/services/pedidos.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CatStatusService } from 'src/app/services/catStatus.service';
import { StoreCatStatus } from 'src/app/objects/StoreCatStatus';

@Component({
  selector: 'app-reporte',
  templateUrl: './reporte.component.html',
  styleUrls: ['./reporte.component.css']
})
@Injectable()
export class ReporteComponent implements OnInit {

  constructor(private pedidosService: PedidosService,private catStatusService:CatStatusService) { }
  ngOnInit() {

    this.filterOrder();
    this.getCatStatus();
  }
  pedidoColumns = ["folio","cliente","contacto","descripcion","estatus","fec_pedido",
                  "fec_entregado","monto_ant","monto_total","monto_pendiente"]
  listPedidos: Pedido[];
  listCatStatus:StoreCatStatus[];
  error = '';
  jsonPaginationPedido: any = {
    pageSize: 5,
    currentPage: 1,
    totalItems: 0,
    pageSizeOptions : []
  };
  jsonPaginationProd: any = {
    pageSize: 5,
    currentPage: 1,
    totalItems: 0,
    pageSizeOptions : []
  };
  filter = {folio:'',cliente:'',estatus:'',fecIni:'',fecFin:''}

  filterOrder(): void {
    this.pedidosService.getPedido(this.filter).subscribe(
      (res: Pedido[]) => {
        this.listPedidos = res;
        this.jsonPaginationPedido={
          pageSize: 5,
          currentPage: 1,
          totalItems: this.listPedidos.length,
          pageSizeOptions : []
        }
        //Genera array de paginas de acuerdo al tama;o del arreglo
        var cont =this.jsonPaginationPedido.pageSize;
        while(cont <=this.jsonPaginationPedido.totalItems){
          
          if((cont +this.jsonPaginationPedido.pageSize)>this.jsonPaginationPedido.totalItems){
            var rest = this.jsonPaginationPedido.totalItems - cont;
            
            if(rest>0) this.jsonPaginationPedido.pageSizeOptions.push(rest);
            
            break;
          }
          this.jsonPaginationPedido.pageSizeOptions.push(cont);
          cont += this.jsonPaginationPedido.pageSize;
        }
      },
      (err) => {
        this.error = err;
      }
    );
  }

  getCatStatus= ()=>{
    this.catStatusService.getCatStatus().subscribe(
      (res:StoreCatStatus[])=>{
        this.listCatStatus = res;
        
      },
      (err)=>{
        console.log(err)
      }
    );
  }
  pageChange(event){
    this.jsonPaginationPedido.currentPage = event;
  }

   
}
