import { ListagemPageMinhasPecasComponent } from './listagem-page-minhas-pecas/listagem-page-minhas-pecas.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
    { path: '', component: ListagemPageMinhasPecasComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MinhasPecasRoutingModule { }
