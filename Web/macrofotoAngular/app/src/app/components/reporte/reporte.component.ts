import { Component, OnInit } from '@angular/core';
import { Pedido } from 'src/app/objects/Pedidos';
import { PedidosService } from 'src/app/services/pedidos.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CatStatusService } from 'src/app/services/catStatus.service';
import { StoreCatStatus } from 'src/app/objects/StoreCatStatus';
import { ProductsPedidoService } from 'src/app/services/productsPedido.service';
import { ProdPedidos } from 'src/app/objects/ProdPedidos';
import { PageEvent } from '@angular/material';

@Component({
  selector: 'app-reporte',
  templateUrl: './reporte.component.html',
  styleUrls: ['./reporte.component.css']
})
@Injectable()
export class ReporteComponent implements OnInit {

  constructor(private pedidosService: PedidosService,
      private catStatusService:CatStatusService,
      private prodPedidoService:ProductsPedidoService,) { }
  ngOnInit() {

    this.filterOrder();
    this.getCatStatus();
  }
  pedidoColumns = ["folio","cliente","contacto","descripcion","estatus","fec_pedido",
                  "fec_entregado","monto_ant","monto_total","monto_pendiente"];
  prodsPedidoColumns = ["bar_code","descripcion","cantidad","costo_unitario","costo_total","estatus"]
  listPedidos: Pedido[];
  listCatStatus:StoreCatStatus[];
  listProdPedido: ProdPedidos[];
  error = '';
  pageEvent: PageEvent; 
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
    pageSizeOptions : [],
    dataSource:[]
  };
  filter = {folio:'',cliente:'',estatus:'',fecIni:'',fecFin:''}
  rowsSelected = {idxOrder:0}
  filterOrder(): void {
    this.pedidosService.getPedido(this.filter).subscribe(
      (res: Pedido[]) => {
        this.listPedidos = res;
        this.jsonPaginationPedido={
          pageSize: 5,
          currentPage: 1,
          totalItems: this.listPedidos.length,
          pageSizeOptions : ['5','10','15','20'],
          dataSource: this.listPedidos.slice(0,5)
        }
        //Genera array de paginas de acuerdo al tama;o del arreglo
        var cont =this.jsonPaginationPedido.pageSize;
        //Consulta Producto del primer registro 
        if(this.jsonPaginationPedido.totalItems>0){
          this.consultProd(this.listPedidos[0],1);
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

  cleanFilter = ()=>{
    this.filter = {folio:'',cliente:'',estatus:'',fecIni:'',fecFin:''}
  }
  pageChange(event){
    this.jsonPaginationPedido.currentPage = event;
  }

  consultProd = (row:Pedido, idx:number) =>{
  //  console.log(row)
    this.rowsSelected.idxOrder = idx;
    this.getProducts(row.id_pedido);

  }
  getProducts = (idPedido:number) => {
    this.prodPedidoService.getProductsByPedido(idPedido).subscribe(
      (res: ProdPedidos[]) =>{
        this.listProdPedido = res;
        this.jsonPaginationProd={
          pageSize: 5,
          currentPage: 1,
          totalItems: this.listProdPedido.length,
          pageSizeOptions : ['5','10','15','20'],
          dataSource:this.listProdPedido.slice(0, 5)
        }
        
        let cont =this.jsonPaginationProd.pageSize;
        
    })
  }

  public getServerDataProd(event?:PageEvent) {
    this.jsonPaginationProd.currentPage = event.pageIndex;
    this.jsonPaginationProd.pageSize = event.pageSize;
    this.iterator(this.listProdPedido,this.jsonPaginationProd);
    return event;
  }
  public getServerDataPedido(event?:PageEvent) {
    this.jsonPaginationPedido.currentPage = event.pageIndex;
    this.jsonPaginationPedido.pageSize = event.pageSize;
    this.iterator(this.listProdPedido,this.jsonPaginationPedido);
    return event;
  }
  private iterator(array:any,jsonPag:any) {
    const end = (jsonPag.currentPage + 1) * jsonPag.pageSize;
    const start = jsonPag.currentPage * jsonPag.pageSize;
    const part = array.slice(start, end);
    this.jsonPaginationProd.dataSource = part;
  }
}
