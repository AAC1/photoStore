import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { ReporteComponent } from './components/reporte/reporte.component';
import { LoginComponent } from './components/login/login.component';


const routes: Routes = [];

const APP_ROUTES: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'reporte-pedidos', component: ReporteComponent },
  { path: '', pathMatch:'full', component: HomeComponent },
 // { path: '**', pathMatch:'full', redirectTo: '' },
];

export const APP_ROUTING = RouterModule.forRoot(
      APP_ROUTES,
      {useHash:true}
    );

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
