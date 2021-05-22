import { TabViewModule } from 'primeng/tabview';
import { MinhasOfertasRoutingModule } from './minhas-ofertas-routing.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListagemPageMinhasOfertasComponent } from './listagem-page-minhas-ofertas/listagem-page-minhas-ofertas.component';
import { SharedModule } from '../shared/shared.module';
import { VirtualScrollerModule } from 'primeng';

@NgModule({
  declarations: [ListagemPageMinhasOfertasComponent],
  imports: [
    CommonModule,
    MinhasOfertasRoutingModule,
    SharedModule,
    VirtualScrollerModule,
    TabViewModule
  ]
})
export class MinhasOfertasModule { }
