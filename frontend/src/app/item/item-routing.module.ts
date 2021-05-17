import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListagemPageItemComponent } from './listagem-page-item/listagem-page-item.component';


const routes: Routes = [
    { path: '', component: ListagemPageItemComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ItemRoutingModule { }
