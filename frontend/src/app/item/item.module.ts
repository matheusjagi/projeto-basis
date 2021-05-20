import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ItemRoutingModule } from './item-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { ListagemPageItemComponent } from './listagem-page-item/listagem-page-item.component';

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
