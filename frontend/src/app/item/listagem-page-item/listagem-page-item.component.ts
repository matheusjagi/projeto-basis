import { ItemService } from './../../services/item.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PageNotificationService } from '@nuvem/primeng-components';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-listagem-page-item',
  templateUrl: './listagem-page-item.component.html',
  styleUrls: ['./listagem-page-item.component.css']
})
export class ListagemPageItemComponent implements OnInit {

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
        let fa = null;

        let reader = new FileReader();
        //get the selected file from event
        let file = event.target.files[0];

        reader.onloadend = () => {
          //Assign the result to variable for setting the src of image element
            this.imageUrl = reader.result;

            fetch(this.imageUrl).then(
               // res => this.form.patchValue({foto: res.blob()})
               res => res.blob()
            ).then(console.log)


          //console.log("Blob:  "+ blob);


        }
        //let fotoAux = new Blob([new Uint8Array(file)], {type: "image/png"});

        //console.log('FOTO BLOB: '+file.);

        //this.form.patchValue({foto: fotoAux});

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
                    this.fecharModal();
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
            this.form.patchValue({usuarioDtoId: 435});

            this.itemService.salvar(this.form.value).pipe(
                finalize(() => {
                    this.fecharModal();
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

    fecharModal(){
        this.form.reset();
        this.isEditing = false;
    }

}
