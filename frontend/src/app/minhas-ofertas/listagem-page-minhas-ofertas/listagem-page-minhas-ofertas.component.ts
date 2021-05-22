import { ItemModel } from './../../models/item-model';
import { PageNotificationService } from '@nuvem/primeng-components';
import { LocalstorageService } from './../../services/localstorage.service';
import { OfertaService } from './../../services/oferta.service';
import { Component, OnInit } from '@angular/core';
import { OfertaModel } from 'src/app/models/oferta-model';

@Component({
    selector: 'app-listagem-page-minhas-ofertas',
    templateUrl: './listagem-page-minhas-ofertas.component.html',
    styleUrls: ['./listagem-page-minhas-ofertas.component.css']
})
export class ListagemPageMinhasOfertasComponent implements OnInit {
    minhasOfertas: OfertaModel[] = [];
    displayVerOfertas: boolean = false;
    itensOfertados: ItemModel[] = [];

    constructor(
        private ofertaService: OfertaService,
        private localstorageService: LocalstorageService,
        private notification: PageNotificationService
    ) { }

    ngOnInit(): void {
        this.buscarMinhasOfertas(this.localstorageService.getId());
    }

    montaImagem(ofertas) {
        ofertas.forEach(oferta => {
            let montandoBase64 = 'data:image/png;base64,';
            let novaString = montandoBase64.concat(oferta.foto);
            oferta.foto = novaString;
        });
    }

    buscarMinhasOfertas(idUsuario) {
        this.ofertaService.buscarPorUsuario(idUsuario)
            .subscribe(
                (ofertas) => {
                    this.montaImagem(ofertas);
                    this.minhasOfertas = ofertas.filter(oferta => {
                        return oferta.situacaoDtoId == 1;
                    })
                },
                () => {
                    this.notification.addErrorMessage("Falha ao carregar a vizualização.");
                }
            )
    }

    mostraOfertas(idOferta) {
        this.ofertaService.buscarPorId(idOferta)
            .subscribe(
                (oferta) => {
                    this.montaImagem(oferta.itensOfertados);
                    this.itensOfertados = oferta.itensOfertados;
                    this.displayVerOfertas = true;
                },
                () => {
                    this.notification.addErrorMessage("Falha ao carregar a vizualização.");
                }
            )
    }

    cancelarOferta(idOferta){
        this.ofertaService.cancelarOferta(idOferta)
            .subscribe(
                () => {
                    this.displayVerOfertas = false;
                    this.notification.addSuccessMessage("Oferta cancelada com sucesso!");
                    this.buscarMinhasOfertas(this.localstorageService.getId());
                },
                () => { this.notification.addErrorMessage("Falha ao cancelar a oferta.") }
            )
    }

}