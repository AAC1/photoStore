import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { AlertModule } from 'ngx-bootstrap';
import {NgxPaginationModule} from 'ngx-pagination'; /* npm install ngx-pagination --save */
/* RUTAS */
import { APP_ROUTING } from './app.routes';


/* SERVICIOS*/



/* COMPONENTES */
import { AppComponent } from './app.component';
import { ConsultaPedidoComponent } from './Components/consultaPedido/app.consultaPedido';
import { HomeComponent } from './Components/home/home.component';

@NgModule({
  declarations: [
    AppComponent,
    ConsultaPedidoComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    APP_ROUTING,
    AlertModule,
    HttpClientModule,
    NgxPaginationModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
