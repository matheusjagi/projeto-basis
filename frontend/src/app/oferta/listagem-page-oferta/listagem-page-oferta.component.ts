import { LocalstorageService } from './../../services/localstorage.service';
import { ItemService } from './../../services/item.service';
import { Component, OnInit } from '@angular/core';
import { PageNotificationService } from '@nuvem/primeng-components';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { OfertaService } from 'src/app/services/oferta.service';
import { finalize } from 'rxjs/operators';
import { ItemModel } from 'src/app/models/item-model';
import { SelectItem } from 'primeng';

@Component({
    selector: 'app-listagem-page-oferta',
    templateUrl: './listagem-page-oferta.component.html',
    styleUrls: ['./listagem-page-oferta.component.css']
})
export class ListagemPageOfertaComponent implements OnInit {

    idItemOferta: number = null;
    form: FormGroup;
    itens: ItemModel[] = [];
    sortCategoria: SelectItem[] = [];
    selectedItem: ItemModel[] = [];
    selectedRemoveItem: ItemModel[] = [];
    selectedItensOfertados: ItemModel[] = [];
    availableItens: ItemModel[] = [];
    draggedItem: ItemModel = null;
    displayDetalhesItem: boolean = false;
    displayTroca: boolean = false;

    constructor(
        private itemService: ItemService,
        private ofertaService: OfertaService,
        private localstorageService: LocalstorageService,
        private fb: FormBuilder,
        private notification: PageNotificationService
    ) { }

    ngOnInit(): void{
        this.iniciarForm();
        this.buscarPorSituacao(true);
        this.buscarCategorias();
    }

    iniciarForm () {
        this.form = this.fb.group({
          id: [null],
          itemDtoId: [null,[Validators.required]],
          usuarioDtoId: [null,[Validators.required]],
          situacaoDtoId: [null,[Validators.required]],
          itensOfertados: [null,[Validators.required]]
        });
    }

    buscarCategorias() {
        this.itemService.buscarCategorias().subscribe(
            (categorias) => {
                this.sortCategoria = categorias.map(categoria => {
                    return {label: categoria.descricao, value: categoria.id}
                });

                this.sortCategoria.unshift({label: 'Pesquisar por Categoria', value: null});
            }
        )
    }

    buscarPorSituacao(situacao: boolean) {
        this.itemService.buscarPorSituacao(situacao).subscribe(
            (itens) => {
                this.itens = this.removerItensUsuarioLogado(itens);
                this.montaImagem(this.itens);
            }
        )
    }

    removerItensUsuarioLogado(itens: ItemModel[]){
        let itensUsuario = itens.filter(item => {
            return item.usuarioDtoId != this.localstorageService.getId();
        })

        return itensUsuario;
    }

    mostrarItem(item) {
        this.selectedItem = item;
        this.displayDetalhesItem = true;
    }

    realizaTroca(item){
        this.displayTroca = true;
        this.selectedItensOfertados = [];
        this.idItemOferta = item.id;

        this.itemService.buscarPorUsuario(this.localstorageService.getId())
        .subscribe(
            (itens) => {
                this.availableItens = itens.filter(item => {
                    return item.disponibilidade;
                });
                this.montaImagem(this.availableItens);
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

    dragStart(event, item) {
        this.draggedItem = item;
    }

    drop(event) {
        if (this.draggedItem) {
            let draggedItemIndex = this.findIndex(this.draggedItem, this.availableItens);
            this.selectedItensOfertados = [...this.selectedItensOfertados, this.draggedItem];
            this.availableItens = this.availableItens.filter((item,index) => index!=draggedItemIndex);
            this.draggedItem = null;
        }
    }

    dragEnd(event) {
        this.draggedItem = null;
    }

    findIndex(item: ItemModel, listaItens: ItemModel[]) {
        return listaItens.indexOf(item);
    }

    removeItem(itensOferta){
        let index =  this.findIndex(itensOferta, this.selectedItensOfertados);
        this.selectedItensOfertados.splice(index,1);
        this.availableItens = [...this.availableItens, itensOferta];
    }

    criarOferta(){
        this.form.patchValue({itemDtoId: this.idItemOferta});
        this.form.patchValue({situacaoDtoId: 1});
        this.form.patchValue({usuarioDtoId: this.localstorageService.getId()});
        this.form.patchValue({itensOfertados: this.selectedItensOfertados.map(item =>
            {
                return{
                    id: item.id, categoriaDtoId: item.categoriaDtoId, usuarioDtoId: item.usuarioDtoId
                }
            })});

        this.ofertaService.salvar(this.form.value).pipe(
            finalize(() => {
                this.limparForm();
            })
        ).subscribe(
            (oferta) => {
                this.displayTroca = false;
                this.notification.addSuccessMessage("Oferta criada com sucesso!");
            },
            () => { this.notification.addErrorMessage("Falha ao realizar a oferta."); }
        )
    }

    limparForm(){
        this.form.reset();
    }

    filterCategoria(idCategoria){
        this.itens = this.itens.filter(item => {
            return item.categoriaDtoId == idCategoria
        })
    }
}
