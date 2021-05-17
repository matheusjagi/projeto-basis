import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from '@nuvem/angular-base';
import { ListagemPageComponent } from './listagem-page/listagem-page.component';


const routes: Routes = [
  { path: '', component: ListagemPageComponent, canActivate: [AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsuarioRoutingModule { }
