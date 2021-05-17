import { ItemService } from './../../services/item.service';
import { Component, OnInit } from '@angular/core';
import { PageNotificationService } from '@nuvem/primeng-components';

@Component({
  selector: 'app-listagem-page-oferta',
  templateUrl: './listagem-page-oferta.component.html',
  styleUrls: ['./listagem-page-oferta.component.css']
})
export class ListagemPageOfertaComponent implements OnInit {

  itens: any[] = [];
  sortCategoria: any[] = [];

  constructor(
      private itemService: ItemService,
      private notification: PageNotificationService
  ) { }

  ngOnInit(): void {
      this.buscarPorSituacao(true);
      this.buscarCategorias();
  }

  buscarCategorias(){
      this.itemService.buscarCategorias().subscribe(
          (categorias) => {
              this.sortCategoria = categorias;
          }
      )
  }

  buscarPorSituacao(situacao: boolean){
    this.itemService.buscarPorSituacao(situacao).subscribe(
        (itens) => {
            this.itens = itens;
        }
    )
  }
}
