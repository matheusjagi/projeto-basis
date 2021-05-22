import { TabViewModule } from 'primeng/tabview';
import { MinhasPecasRoutingModule } from './minhas-pecas-routing.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListagemPageMinhasPecasComponent } from './listagem-page-minhas-pecas/listagem-page-minhas-pecas.component';
import { VirtualScrollerModule } from 'primeng';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [ListagemPageMinhasPecasComponent],
  imports: [
    CommonModule,
    MinhasPecasRoutingModule,
    SharedModule,
    VirtualScrollerModule,
    TabViewModule
  ]
})
export class MinhasPecasModule { }
