import { environment } from './../../environments/environment';
import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  private api = environment.apiUrl;

  constructor(private http: HttpClient) { }

  buscarTodos () {
    return this.http.get<any[]>(`api/itens`);
  }

  buscarPorId (idItem) {
    return this.http.get<any>(`api/itens/${idItem}`);
  }

  salvar (item) {
    return this.http.post(`api/itens`, item);
  }

  atualizar (item) {
    return this.http.put(`api/itens`, item);
  }

  excluir (id) {
    return this.http.delete(`api/itens/${id}`);
  }

}
