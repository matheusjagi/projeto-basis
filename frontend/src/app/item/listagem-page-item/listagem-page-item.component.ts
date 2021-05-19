import { ItemService } from './../../services/item.service';
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PageNotificationService } from '@nuvem/primeng-components';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-listagem-page-item',
  templateUrl: './listagem-page-item.component.html',
  styleUrls: ['./listagem-page-item.component.css']
})
export class ListagemPageItemComponent implements OnInit {

    @ViewChild('inputFile', {static:false}) inputFile: ElementRef;
    itens: any[] = [];
    categorias: any[] = [];
    form: FormGroup;
    submit: boolean = false;
    isEditing: boolean = false;
    imageUrl: any;

    constructor(
        private itemService: ItemService,
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

        if(this.isEditing){

            this.itemService.atualizar(this.form.value)
            .pipe(
                finalize(() => {
                    this.submit = false;
                    this.limparForm();
                })
            ).subscribe(
                () => {
                    this.notification.addSuccessMessage("Item atualizado com sucesso!");
                },
                () => {
                    this.notification.addErrorMessage("Falha ao atualizar o cadastro.");
                }
            )

        } else {
            this.form.patchValue({disponibilidade: true});
            this.form.patchValue({usuarioDtoId: JSON.parse(localStorage.getItem('usuario')).id});

            this.itemService.salvar(this.form.value).pipe(
                finalize(() => {
                    this.limparForm();
                    this.submit = false;
                })
            ).subscribe(
                (item) => {
                    this.notification.addSuccessMessage("Item criado com sucesso!");
                },
                () => {
                    this.notification.addErrorMessage("Falha ao realizar o cadastro.");
                }
            )
        }
    }

    editar(idItem){
        this.isEditing = true;

        this.itemService.buscarPorId(idItem).subscribe(
          (item) => {
              this.form.patchValue(item);
          }
        )
    }

    excluir(idItem){
        console.log(idItem);
        this.itemService.excluir(idItem).subscribe(
            () => {
                this.notification.addSuccessMessage("Item excluido com sucesso!");
            },
            () => {
                this.notification.addErrorMessage("Falha ao excluir item.");
            }
        )
    }

    limparForm(){
        this.form.reset();
        this.imageUrl = '';
        this.inputFile.nativeElement.value = '';
    }

}
