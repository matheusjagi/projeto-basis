import { ListagemPageItemComponent } from './listagem-page-item/listagem-page-item.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';

import { ItemRoutingModule } from './item-routing.module';


@NgModule({
  declarations: [
    ListagemPageItemComponent
  ],
  imports: [
    CommonModule,
    ItemRoutingModule,
    SharedModule,
    ReactiveFormsModule
  ]
})
export class ItemModule { }
