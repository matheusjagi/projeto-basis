import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DiarioErrosComponent } from './components/diario-erros/diario-erros.component';
import { LoginSuccessComponent } from '@nuvem/angular-base';

const routes: Routes = [
    { path: 'diario-erros', component: DiarioErrosComponent, data: { breadcrumb: 'Diário de Erros'} },
    { path: 'login-success', component: LoginSuccessComponent },
    { path: 'usuarios', loadChildren: () => import('./usuario/usuario.module').then( m=> m.UsuarioModule) },
    { path: 'itens', loadChildren: () => import('./item/item.module').then( m=> m.ItemModule) },
    { path: 'ofertas', loadChildren: () => import('./oferta/oferta.module').then( m=> m.OfertaModule) }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
