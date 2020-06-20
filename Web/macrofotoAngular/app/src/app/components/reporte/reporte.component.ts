import { Component, OnInit,ViewChild, ElementRef } from '@angular/core';
import { Pedido } from 'src/app/objects/Pedidos';
import { PedidosService } from 'src/app/services/pedidos.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CatStatusService } from 'src/app/services/catStatus.service';
import { StoreCatStatus } from 'src/app/objects/StoreCatStatus';
import { ProductsPedidoService } from 'src/app/services/productsPedido.service';
import { ProdPedidos } from 'src/app/objects/ProdPedidos';
//import { PageEvent } from '@angular/material';
import { ExportFileService } from 'src/app/services/exportFile.service';
import { PageEvent } from '@angular/material/paginator';

import XlsxPopulate from 'xlsx-populate/browser/xlsx-populate.min';

import FileSaver from 'file-saver';

const EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';//vnd.ms-excel';//octet-stream';//
const EXCEL_EXTENSION = 'xlsx';

@Component({
  selector: 'app-reporte',
  templateUrl: './reporte.component.html',
  styleUrls: ['./reporte.component.css']
})
@Injectable()
export class ReporteComponent implements OnInit {

  constructor(private pedidosService: PedidosService,
      private catStatusService:CatStatusService,
      private prodPedidoService:ProductsPedidoService,
      private exportFileService:ExportFileService) { }

  ngOnInit() {

    this.filterOrder();
    this.getCatStatus();
  }

  @ViewChild('tblPedido') tblPedido: ElementRef<HTMLElement>;
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
  filterOrigin = {folio:'',cliente:'',estatus:'',fecIni:'',fecFin:''}
  rowsSelected = {idxOrder:0}
  
  getReport = ()=>{
    
    this.exportFileService.getDataExport(this.filterOrigin).subscribe(
      (res:any) =>{
        console.log(res);
        this.createXLS(res);
      }
    ),
    (err) => {
      this.error = err;

    }
    
  }
  createXLS =(listExport)=>{
    const bgDark =  "034165"
      
    const colPedidoStyle ="B2B2B2"
     
    const fontColor = "ffffff"
    XlsxPopulate.fromBlankAsync()
      .then(workbook => {
        const orderSheet = workbook.sheet("Sheet1")
        // Modify the workbook.
        orderSheet.column("A").width(25);
        orderSheet.column("B").width(25);
        orderSheet.column("C").width(30);
        orderSheet.column("D").width(40);
        orderSheet.column("E").width(12);
        orderSheet.column("F").width(20);
        orderSheet.column("G").width(20);
        orderSheet.column("H").width(20);
        orderSheet.column("I").width(20);
        orderSheet.column("J").width(20);

        orderSheet.cell("A1").value("Folio").style("fill",bgDark).style("fontColor",fontColor);
        orderSheet.cell("B1").value("Cliente").style("fill",bgDark).style("fontColor",fontColor);
        orderSheet.cell("C1").value("Contacto").style("fill",bgDark).style("fontColor",fontColor);
        orderSheet.cell("D1").value("Desccripci\u00F3n").style("fill",bgDark).style("fontColor",fontColor);
        orderSheet.cell("E1").value("Estatus").style("fill",bgDark).style("fontColor",fontColor);
        orderSheet.cell("F1").value("Fec. Del pedido").style("fill",bgDark).style("fontColor",fontColor)
        orderSheet.cell("G1").value("Fec. Entregado").style("fill",bgDark).style("fontColor",fontColor);
        orderSheet.cell("H1").value("Monto de Anticipo").style("fill",bgDark).style("fontColor",fontColor);
        orderSheet.cell("I1").value("Costo total").style("fill",bgDark).style("fontColor",fontColor);
        orderSheet.cell("J1").value("Monto pendiente").style("fill",bgDark).style("fontColor",fontColor);
        var cont=2;
        listExport.forEach(el => {
          orderSheet.cell("A"+cont).value(el.folio).style("fill",colPedidoStyle);
          orderSheet.cell("B"+cont).value(el.cliente).style("fill",colPedidoStyle);
          orderSheet.cell("C"+cont).value(el.telefono).style("fill",colPedidoStyle);
          orderSheet.cell("D"+cont).value(el.descripcion).style("fill",colPedidoStyle);
          orderSheet.cell("E"+cont).value(el.catEstatus.estatus).style("fill",colPedidoStyle);
          orderSheet.cell("F"+cont).value(el.fec_pedido && el.fec_pedido.length>0?new Date(el.fec_pedido):"").style("fill",colPedidoStyle).style("numberFormat", "dd/MM/yyyy");
          orderSheet.cell("G"+cont).value(el.fec_entregado && el.fec_entregado.length>0?new Date(el.fec_entregado):"").style("fill",colPedidoStyle).style("numberFormat", "dd/MM/yyyy");
          orderSheet.cell("H"+cont).value(el.monto_ant).style("fill",colPedidoStyle).style("numberFormat", "0.00");
          orderSheet.cell("I"+cont).value(el.costo_total).style("fill",colPedidoStyle).style("numberFormat", "0.00");
          orderSheet.cell("J"+cont).value(el.costo_total - el.monto_ant).style("fill",colPedidoStyle).style("numberFormat", "0.00");
          cont++;
          //HEADER PROD
         // orderSheet.cell("A"+cont).value("").style("fill",bgDark).style("fontColor",fontColor);
          orderSheet.cell("B"+cont).value("C\u00F3digo de barras").style("fill",bgDark).style("fontColor",fontColor);
          orderSheet.cell("C"+cont).value("Desccripci\u00F3n").style("fill",bgDark).style("fontColor",fontColor);
          orderSheet.cell("D"+cont).value("Cantidad").style("fill",bgDark).style("fontColor",fontColor);
          orderSheet.cell("E"+cont).value("Costo unitario").style("fill",bgDark).style("fontColor",fontColor);
          orderSheet.cell("F"+cont).value("Costo total").style("fill",bgDark).style("fontColor",fontColor);
          orderSheet.cell("G"+cont).value("Estatus").style("fill",bgDark).style("fontColor",fontColor);
          orderSheet.cell("H"+cont).value("").style("fill",bgDark).style("fontColor",fontColor);
          orderSheet.cell("I"+cont).value("").style("fill",bgDark).style("fontColor",fontColor);
          orderSheet.cell("J"+cont).value("").style("fill",bgDark).style("fontColor",fontColor);
          cont++;
          (el.products).forEach((elProd:any )=> {
            orderSheet.cell("B"+cont).value(elProd.bar_code);
            orderSheet.cell("C"+cont).value(elProd.descripcion);
            orderSheet.cell("D"+cont).value(elProd.cantidad);
            orderSheet.cell("E"+cont).value(elProd.costo_unitario);
            orderSheet.cell("F"+cont).value(elProd.costo_total);
            orderSheet.cell("G"+cont).value(elProd.estatus);
            cont++;
          });
          cont++;
        });
   //     console.log(orderSheet);

        this.saveFileWb(workbook);
      
      });
    


  }
  saveFileWb =(wb)=>{
    wb.outputAsync()
      .then(function (blob) {
        FileSaver.saveAs(blob, "Pedido" + '_' + new Date().getTime() +'.'+ EXCEL_EXTENSION);
       
    });
  }
  /*
  getReport2 = ()=>{
    
    let element = document.getElementById('tblPedido'); 
    console.log(element);
    const ws =XLSX.utils.table_to_sheet(element);
    
    ws["!cols"] = [{
        wpx: 150
    }, {
        wpx: 120
    }, {
        wpx: 70
    }, {
        wpx: 130
    }, {
        wpx: 80
    }, {
      wpx: 100
    }, {
      wpx: 100
    }, {
      wpx: 100
    }, {
      wpx: 100
    }, {
      wpx: 100
    }];
    ws['!ref'] = "A1:AW100000";

    const styleHeader = {
      font: {
        name: 'Arial',
        sz: 18,
        bold: true,
        color: {
            rgb: "FFFFFF00"
        }
      },
      alignment: { horizontal: "center", vertical: "center", wrap_text: true },
      fill: {
        bgColor: { rgb: 'FFFFAA00' }
      },
      border:{
        bottom:{style:"dotted"}
      }
    };

    ws["A1"].s = styleHeader;
    ws["B1"].s = styleHeader;
    ws["C1"].s = styleHeader;
    ws["D1"].s = styleHeader;
    ws["E1"].s = styleHeader;
    ws["F1"].s = styleHeader;
    ws["G1"].s = styleHeader;
    ws["H1"].s = styleHeader;
    ws["I1"].s = styleHeader;
    ws["J1"].s = styleHeader;

    var workbook = {
        SheetNames: ["Pedido"],
        Sheets: {}
    };
    workbook.Sheets["Pedido"] = ws;
    var wopts = {
        bookType: EXCEL_EXTENSION, // File type to generate
        bookSST: false, // Whether to generate Shared String Table or not, the official explanation is that the build speed will decrease if turned on, but there is better compatibility on lower version IOS devices
        type: 'binary'
    };
  //  var wbout = XLSXStyle.write(workbook, wopts);

   // const wb: XLSXStyle.WorkBook = { Sheets: { 'data': ws }, SheetNames: ['data'] };
    const wbout: any = XLSXStyle.write(workbook, wopts);
    console.log(ws)
    //console.log(wbout)
   // XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');

//    XLSX.writeFile(wb, "Pedido_.xlsx");
    this.saveAsExcelFile(wbout,"Pedido")
  }
*/
  
/*
private saveAsExcelFile(dataBinary: any, fileName: string): void {
    const data: Blob = new Blob([this.s2ab(dataBinary)], {
      type: EXCEL_TYPE
    });
    FileSaver.saveAs(data, fileName + '_' + new Date().getTime() +'.'+ EXCEL_EXTENSION);
  }
  private s2ab(s) {
    var buf = new ArrayBuffer(s.length);
    var view = new Uint8Array(buf);
    for (var i=0; i!=s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF;
    return buf;
  }*/
  filterOrder(): void {
    this.filterOrigin = this.filter;
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
