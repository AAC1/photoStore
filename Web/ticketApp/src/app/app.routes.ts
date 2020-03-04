import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ConsultaPedidoComponent } from './Components/consultaPedido/app.consultaPedido';
import { HomeComponent } from './Components/home/home.component';

const APP_ROUTES: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'consulta-pedidos', component: ConsultaPedidoComponent },
  { path: '**', pathMatch:'full', redirectTo: 'home' },
];

export const APP_ROUTING = RouterModule.forRoot(APP_ROUTES,{useHash:true});
/*
@NgModule({
  imports: [RouterModule.forRoot(APP_ROUTES)],
  exports: [RouterModule]
})
*/
export class AppRoutingModule { }
