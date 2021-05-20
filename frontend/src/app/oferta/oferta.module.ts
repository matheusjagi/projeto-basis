import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OfertaRoutingModule } from './oferta-routing.module';
import { SharedModule } from '../shared/shared.module';
import { ListagemPageOfertaComponent } from './listagem-page-oferta/listagem-page-oferta.component';
import { DragDropModule } from 'primeng';

@NgModule({
  declarations: [
      ListagemPageOfertaComponent
 ],
  imports: [
    CommonModule,
    OfertaRoutingModule,
    SharedModule,
    DragDropModule
  ]
})
export class OfertaModule { }
