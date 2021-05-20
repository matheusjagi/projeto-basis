import { CategoriaModel } from './../../models/categoria-model';
import { ItemModel } from 'src/app/models/item-model';
import { LocalstorageService } from './../../services/localstorage.service';
import { ItemService } from './../../services/item.service';
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PageNotificationService } from '@nuvem/primeng-components';
import { finalize } from 'rxjs/operators';
import { SelectItem } from 'primeng';

@Component({
  selector: 'app-listagem-page-item',
  templateUrl: './listagem-page-item.component.html',
  styleUrls: ['./listagem-page-item.component.css']
})
export class ListagemPageItemComponent implements OnInit {

    @ViewChild('inputFile', {static:false}) inputFile: ElementRef;
    itens: ItemModel[] = [];
    categorias: SelectItem[] = [];
    form: FormGroup;
    submit: boolean = false;
    imageUrl: any;

    constructor(
        private itemService: ItemService,
        private localstorageService: LocalstorageService,
        private fb: FormBuilder,
        private notification: PageNotificationService
    ) { }

    ngOnInit(): void {
        this.iniciarForm();
        this.buscarCategorias();
        this.buscarItens();
    }

    buscarItens(){
        this.itemService.buscarTodos().subscribe(
            (itens) => {
                this.itens = itens;
            }
        )
    }

    iniciarForm () {
        this.form = this.fb.group({
          id: [null],
          nome: [null,[Validators.required]],
          descricao: [null,[Validators.required]],
          foto: [null,[Validators.required]],
          disponibilidade: [null,[Validators.required]],
          usuarioDtoId: [null],
          categoriaDtoId: [null]
        });
    }

    imageUpload(event){
        let reader = new FileReader();
        let file = event.target.files[0];

        reader.onloadend = () => {
            this.imageUrl = reader.result;
            let blob = this.imageUrl.split(",");
            this.form.patchValue({foto: blob[1]});
        }

        reader.readAsDataURL(file);
    }

    buscarCategorias(){
        this.itemService.buscarCategorias().subscribe(
            (categorias) => {
                this.categorias = categorias.map(categoria => {
                    return {label: categoria.descricao, value: categoria.id}
                });
            }
        )
    }

    salvar(){
        this.submit = true;
        this.form.patchValue({disponibilidade: true});
        this.form.patchValue({usuarioDtoId: this.localstorageService.getId()});

        this.itemService.salvar(this.form.value).pipe(
            finalize(() => {
                this.limparForm();
                this.submit = false;
            })
        ).subscribe(
            (item) => { this.notification.addSuccessMessage("Item criado com sucesso!") },
            () => { this.notification.addErrorMessage("Falha ao realizar o cadastro.") }
        )
    }

    editar(idItem){
        this.itemService.buscarPorId(idItem).subscribe(
          (item) => {
              this.form.patchValue(item);
          }
        )
    }

    excluir(idItem){
        this.itemService.excluir(idItem).subscribe(
            () => { this.notification.addSuccessMessage("Item excluido com sucesso!") },
            () => { this.notification.addErrorMessage("Falha ao excluir item.") }
        )
    }

    limparForm(){
        this.form.reset();
        this.imageUrl = '';
        this.inputFile.nativeElement.value = '';
    }

}
