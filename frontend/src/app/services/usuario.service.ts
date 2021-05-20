import { UsuarioModel } from './../models/usuario-model';
import { environment } from './../../environments/environment';
import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private api = environment.apiUrl;

  constructor(private http: HttpClient) { }

  buscarTodos () {
    return this.http.get<UsuarioModel[]>(`api/usuarios`);
  }

  buscarPorId (idUsuario) {
    return this.http.get<UsuarioModel>(`api/usuarios/${idUsuario}`);
  }

  salvar (usuario) {
    return this.http.post(`api/usuarios`, usuario);
  }

  atualizar (usuario) {
    return this.http.put(`api/usuarios`, usuario);
  }

  excluir (id) {
    return this.http.delete(`api/usuarios/${id}`);
  }

}
