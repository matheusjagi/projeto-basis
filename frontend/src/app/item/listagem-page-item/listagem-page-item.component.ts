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
  displayModal: boolean = false;
  form: FormGroup;
  submit: boolean = false;
  isEditing: boolean = false;

  constructor(
      private itemService: ItemService,
      private fb: FormBuilder,
      private notification: PageNotificationService) { }

  ngOnInit(): void {
    this.buscarTodos();
    this.iniciarForm();
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

  buscarTodos () {
    this.itemService.buscarTodos().subscribe(
      (itens) => {
        this.itens = itens;
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
                this.buscarTodos();
            },
            () => {
                this.notification.addErrorMessage("Falha ao atualizar cadastro.");
            }
        )

    } else {

        this.itemService.salvar(this.form.value).pipe(
            finalize(() => {
                this.fecharModal();
                this.submit = false;
            })
        ).subscribe(
            (item) => {
                this.notification.addSuccessMessage("Item criado com sucesso!");
                this.buscarTodos();
            },
            () => {
                this.notification.addErrorMessage("Falha ao realizar cadastro.");
            }
        )
    }
  }

  editar(idItem){
      this.isEditing = true;

      this.itemService.buscarPorId(idItem).subscribe(
        (item) => {
            this.displayModal = true;
            this.form.patchValue(item);
        }
      )
  }

  excluir(idItem){
    console.log(idItem);
    this.itemService.excluir(idItem).subscribe(
        () => {
            this.notification.addSuccessMessage("Item excluido com sucesso!");
            this.buscarTodos();
        },
        () => {
            this.notification.addErrorMessage("Falha ao excluir item.");
        }
    )
  }

  abrirModal(){
    this.displayModal = true;
  }

  fecharModal(){
    this.form.reset();
    this.displayModal = false;
    this.isEditing = false;
  }

}
