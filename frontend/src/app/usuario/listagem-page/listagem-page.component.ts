import { UsuarioService } from './../../services/usuario.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PageNotificationService } from '@nuvem/primeng-components';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-listagem-page',
  templateUrl: './listagem-page.component.html',
  styleUrls: ['./listagem-page.component.css']
})
export class ListagemPageComponent implements OnInit {

  usuarios: any[] = [];
  displayModal: boolean = false;
  form: FormGroup;
  submit: boolean = false;
  isEditing: boolean = false;

  constructor(
      private usuarioService: UsuarioService,
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
      email: [null,[Validators.required, Validators.email]],
      cpf: [null,[Validators.required, Validators.maxLength(11), Validators.minLength(11)]],
      dataNascimento: [null,[Validators.required]]
    });
  }

  buscarTodos () {
    this.usuarioService.buscarTodos().subscribe(
      (usuarios) => {
        this.usuarios = usuarios;
      }
    )
  }

  salvar(){
    this.submit = true;

    if(this.isEditing){

        this.usuarioService.atualizar(this.form.value)
        .pipe(
            finalize(() => {
                this.submit = false;
                this.fecharModal();
            })
        ).subscribe(
            () => {
                this.notification.addSuccessMessage("Usuário atualizado com sucesso!");
                this.buscarTodos();
            },
            () => {
                this.notification.addErrorMessage("Falha ao atualizar cadastro.");
            }
        )

    } else {

        this.usuarioService.salvar(this.form.value).pipe(
            finalize(() => {
                this.fecharModal();
                this.submit = false;
            })
        ).subscribe(
            (usuario) => {
                this.notification.addSuccessMessage("Usuário criado com sucesso!");
                this.buscarTodos();
            },
            () => {
                this.notification.addErrorMessage("Falha ao realizar cadastro.");
            }
        )
    }
  }

  editar(idUsuario){
      this.isEditing = true;

      this.usuarioService.buscarPorId(idUsuario).subscribe(
        (usuario) => {
            this.displayModal = true;
            this.form.patchValue(usuario);
            /*this.form.patchValue({
                ...usuario,
                dataNascimento: new Date(usuario.dataNascimento)
            })*/
        }
      )
  }

  excluir(idUsuario){
    console.log(idUsuario);
    this.usuarioService.excluir(idUsuario).subscribe(
        () => {
            this.notification.addSuccessMessage("Usuário excluido com sucesso!");
            this.buscarTodos();
        },
        () => {
            this.notification.addErrorMessage("Falha ao excluir usuário.");
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
