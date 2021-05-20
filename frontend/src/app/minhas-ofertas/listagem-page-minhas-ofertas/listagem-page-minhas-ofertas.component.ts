import { LocalstorageService } from './../../services/localstorage.service';
import { ItemService } from './../../services/item.service';
import { ItemModel } from 'src/app/models/item-model';
import { Component, OnInit } from '@angular/core';
import { OfertaService } from 'src/app/services/oferta.service';

@Component({
    selector: 'app-listagem-page-minhas-ofertas',
    templateUrl: './listagem-page-minhas-ofertas.component.html',
    styleUrls: ['./listagem-page-minhas-ofertas.component.css']
})
export class ListagemPageMinhasOfertasComponent implements OnInit {

    meusItens: ItemModel[] = [];

    constructor(
        private itemService: ItemService,
        private ofertaService: OfertaService,
        private localstorageService: LocalstorageService,
    ) { }

    ngOnInit(): void {
        this.buscarMeusItens();
    }

    buscarMeusItens(){
        this.itemService.buscarPorUsuario(this.localstorageService.getId())
            .subscribe(
                (itens) => {
                    this.meusItens = itens;
                    this.montaImagem(this.meusItens);
                }
            )
    }

    montaImagem(itens){
        itens.forEach(item => {
            let montandoBase64 = 'data:image/png;base64,';
            let novaString = montandoBase64.concat(item.foto);
            item.foto = novaString;
        });
    }
}
