import { OfertaModel } from './../../models/oferta-model';
import { ItemModel } from './../../models/item-model';
import { OfertaService } from './../../services/oferta.service';
import { LocalstorageService } from './../../services/localstorage.service';
import { ItemService } from './../../services/item.service';
import { Component, OnInit } from '@angular/core';
import { SelectItem } from 'primeng';
import { PageNotificationService } from '@nuvem/primeng-components';

@Component({
    selector: 'app-listagem-page-minhas-ofertas',
    templateUrl: './listagem-page-minhas-ofertas.component.html',
    styleUrls: ['./listagem-page-minhas-ofertas.component.css']
})
export class ListagemPageMinhasOfertasComponent implements OnInit {

    minhasOfertas: OfertaModel[] = [];
    meusItens: ItemModel[] = [];
    itensOfertados: ItemModel[] = [];
    displayVerOfertas: boolean = false;
    selectedItem: ItemModel;
    selectedOferta: OfertaModel;
    sortOferta: SelectItem[] = [];

    constructor(
        private itemService: ItemService,
        private ofertaService: OfertaService,
        private localstorageService: LocalstorageService,
        private notification: PageNotificationService
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

    mostraOfertas(item: ItemModel){
        this.selectedItem = item;

        this.ofertaService.buscarPorItem(item.id)
            .subscribe(
                (ofertas) => {
                    if(!Object.values(ofertas).length){
                        console.log("Oferta: ",ofertas);
                        this.notification.addWarnMessage("Não existem ofertas para essa peça!");
                    }
                    else{
                        this.displayVerOfertas = true;
                        this.minhasOfertas = ofertas;

                        this.minhasOfertas.forEach(oferta => {
                            this.montaImagem(oferta.itensOfertados);
                        })

                        this.sortOferta = this.minhasOfertas.map(oferta => {
                            let index = this.minhasOfertas.indexOf(oferta) + 1;
                            return {label: `${index}`, value: oferta.id}
                        });
                    }
                },
                () => {
                    this.notification.addErrorMessage("Falha ao carregar as ofertas.");
                }
            )

        this.minhasOfertas.forEach(oferta => {
            this.meusItens.push.apply(this.meusItens, oferta.itensOfertados);
        })

    }

    aceitarOferta(){
        this.ofertaService.aceitarOferta(this.selectedOferta.id)
            .subscribe(
                () => {
                    this.displayVerOfertas = false;
                    this.notification.addSuccessMessage("Oferta aceita com sucesso!");
                    this.buscarMeusItens();
                },
                () => { this.notification.addErrorMessage("Falha ao aceitar a oferta.") }
            )
    }

    adicionaOfertaClicada(idOferta){
        this.selectedOferta = new OfertaModel();
        this.selectedOferta.id = idOferta;
    }

    incrementaIndex(index){
        return index + 1;
    }
}