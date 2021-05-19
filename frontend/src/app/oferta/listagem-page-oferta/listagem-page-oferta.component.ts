import { ItemService } from './../../services/item.service';
import { Component, OnInit } from '@angular/core';
import { PageNotificationService } from '@nuvem/primeng-components';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { OfertaService } from 'src/app/services/oferta.service';
import { finalize } from 'rxjs/operators';

@Component({
    selector: 'app-listagem-page-oferta',
    templateUrl: './listagem-page-oferta.component.html',
    styleUrls: ['./listagem-page-oferta.component.css']
})
export class ListagemPageOfertaComponent implements OnInit {

    idItemOferta: number = null;
    form: FormGroup;
    itens: any[] = [];
    sortCategoria: any[] = [];
    selectedItem: any[] = [];
    selectedRemoveItem: any[] = [];
    selectedItensOfertados: any[] = [];
    availableItens: any[] = [];
    draggedItem: any = null;
    displayDetalhesItem: boolean = false;
    displayTroca: boolean = false;
    isEditing: boolean = false;

    constructor(
        private itemService: ItemService,
        private ofertaService: OfertaService,
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
                this.sortCategoria = categorias;
            }
        )
    }

    buscarPorSituacao(situacao: boolean) {
        this.itemService.buscarPorSituacao(situacao).subscribe(
            (itens) => {
                this.itens = itens;
                this.montaImagem(this.itens);
            }
        )
    }

    mostrarItem(item) {
        this.selectedItem = item;
        this.displayDetalhesItem = true;
    }

    realizaTroca(item){
        this.displayTroca = true;
        this.selectedItensOfertados = [];
        this.idItemOferta = item.id;

        this.itemService.buscarPorUsuario(JSON.parse(localStorage.getItem('usuario')).id)
        .subscribe(
            (itens) => {
                this.availableItens = itens;
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
            this.availableItens = this.availableItens.filter((val,i) => i!=draggedItemIndex);
            this.draggedItem = null;
        }
    }

    dragEnd(event) {
        this.draggedItem = null;
    }

    findIndex(item, listaItens) {
        let index = -1;
        for(let i = 0; i < listaItens.length; i++) {
            if (item.id === listaItens[i].id) {
                index = i;
                break;
            }
        }
        return index;
    }

    removeItem(itensOferta){
        let index =  this.findIndex(itensOferta, this.selectedItensOfertados);
        this.selectedItensOfertados.splice(index,1);
        this.availableItens = [...this.availableItens, itensOferta];
    }

    criarOferta(){

        if(this.isEditing){

            this.ofertaService.atualizar(this.form.value)
            .pipe(
                finalize(() => {
                    this.limparForm();
                })
            ).subscribe(
                () => {
                    this.notification.addSuccessMessage("Oferta atualizada com sucesso!");
                },
                () => {
                    this.notification.addErrorMessage("Falha ao atualizar a oferta.");
                }
            )

        } else {
            this.form.patchValue({itemDtoId: this.idItemOferta});
            this.form.patchValue({situacaoDtoId: 1});
            this.form.patchValue({usuarioDtoId: JSON.parse(localStorage.getItem('usuario')).id});
            // pegar os itens ofertados


            this.ofertaService.salvar(this.form.value).pipe(
                finalize(() => {
                    this.limparForm();
                })
            ).subscribe(
                (oferta) => {
                    this.notification.addSuccessMessage("Oferta criada com sucesso!");
                },
                () => {
                    this.notification.addErrorMessage("Falha ao realizar a oferta.");
                }
            )
        }
    }

    limparForm(){
        this.form.reset();
    }
}
