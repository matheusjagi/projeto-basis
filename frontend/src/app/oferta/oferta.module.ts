import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OfertaRoutingModule } from './oferta-routing.module';
import { SharedModule } from '../shared/shared.module';
import { ListagemPageOfertaComponent } from './listagem-page-oferta/listagem-page-oferta.component';

@NgModule({
  declarations: [
      ListagemPageOfertaComponent
 ],
  imports: [
    CommonModule,
    OfertaRoutingModule,
    SharedModule
  ]
})
export class OfertaModule { }
