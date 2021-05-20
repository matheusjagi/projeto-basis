import { ListagemPageMinhasOfertasComponent } from './listagem-page-minhas-ofertas/listagem-page-minhas-ofertas.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
    { path: '', component: ListagemPageMinhasOfertasComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MinhasOfertasRoutingModule { }
