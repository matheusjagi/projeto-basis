import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListagemPageOfertaComponent } from './listagem-page-oferta/listagem-page-oferta.component';

const routes: Routes = [
    { path: '', component: ListagemPageOfertaComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OfertaRoutingModule { }
