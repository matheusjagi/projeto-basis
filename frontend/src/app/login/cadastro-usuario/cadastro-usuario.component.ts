import { UsuarioService } from './../../services/usuario.service';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { finalize } from 'rxjs/operators';
import { MessageService } from 'primeng';

@Component({
  selector: 'app-cadastro-usuario',
  templateUrl: './cadastro-usuario.component.html',
  styleUrls: ['./cadastro-usuario.component.css']
})
export class CadastroUsuarioComponent implements OnInit {

  usuarios: any[] = [];
  @Input() displayModal: boolean = false;
  @Output() fechar = new EventEmitter;
  form: FormGroup;
  submit: boolean = false;

  constructor(
      private usuarioService: UsuarioService,
      private fb: FormBuilder,
      private messageService: MessageService) { }

  ngOnInit(): void {
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

  salvar(){
    this.submit = true;
    this.usuarioService.salvar(this.form.value).pipe(
    finalize(() => {
      this.fecharModal();
      this.submit = false;
    })).subscribe(
      (usuario) => {
        this.messageService.add({severity:'success', summary:'Sucesso', detail:'Usuário criado com sucesso!'});
      },
    () => {
        this.messageService.add({severity:'error', summary:'Falha', detail:'Falha ao cadastrar'});
      }
    )
    
  }

  fecharModal(){
    this.form.reset();
    this.displayModal = false;
    this.fechar.emit(false);
  }

  cadastrarUsuario(){
    return this.form.invalid || this.submit;
  }
}
