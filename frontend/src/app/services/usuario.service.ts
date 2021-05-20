import { UsuarioModel } from './../models/usuario-model';
import { environment } from './../../environments/environment';
import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private api: string = environment.apiUrl + "/usuarios";

  constructor(private http: HttpClient) { }

  buscarTodos () {
    return this.http.get<UsuarioModel[]>(`${this.api}`);
  }

  buscarPorId (idUsuario) {
    return this.http.get<UsuarioModel>(`${this.api}/${idUsuario}`);
  }

  salvar (usuario) {
    return this.http.post(`${this.api}`, usuario);
  }

  atualizar (usuario) {
    return this.http.put(`${this.api}`, usuario);
  }

  excluir (id) {
    return this.http.delete(`${this.api}/${id}`);
  }

}
