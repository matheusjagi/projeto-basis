import { UsuarioService } from './../../services/usuario.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-listagem-page',
  templateUrl: './listagem-page.component.html',
  styleUrls: ['./listagem-page.component.css']
})
export class ListagemPageComponent implements OnInit {

  usuarios: any[] = [];
  displayModal: boolean = false;
  form: FormGroup;

  constructor(private usuarioService: UsuarioService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.buscarTodos();
    this.iniciarForm();
  }

  iniciarForm () {
    this.form = this.fb.group({
      nome: [],
      email: [],
      cpf: [],
      dataNascimento: []
    });
  }

  buscarTodos () {
    this.usuarioService.buscarTodos().subscribe(
      (usuarios) => {
        this.usuarios = usuarios
      }
    )
  }

  salvarUsuario () {
    this.usuarioService.salvar(this.form.value);
  }

  abrirModal(){
    this.displayModal = true;
  }

}
