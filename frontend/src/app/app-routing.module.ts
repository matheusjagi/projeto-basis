import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DiarioErrosComponent } from './components/diario-erros/diario-erros.component';
import { LoginSuccessComponent } from '@nuvem/angular-base';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './guard/auth.guard';
import { GuestGuard } from './guard/guest.guard';

const routes: Routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full'},
    { path: 'admin', loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule), canActivate: [AuthGuard]},
    { path: 'diario-erros', component: DiarioErrosComponent, data: { breadcrumb: 'Diário de Erros'} },
    { path: 'login-success', component: LoginSuccessComponent },
    { path: 'login', component: LoginComponent, canActivate: [GuestGuard]},
    { path: 'itens', loadChildren: () => import('./item/item.module').then( m=> m.ItemModule) },
    { path: 'ofertas', loadChildren: () => import('./oferta/oferta.module').then( m=> m.OfertaModule) },
    
    
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
