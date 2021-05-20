import { CategoriaModel } from './../models/categoria-model';
import { environment } from './../../environments/environment.prod';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ItemModel } from '../models/item-model';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

    private api = environment.apiUrl;

    constructor(private http: HttpClient) { }

    buscarCategorias(){
      return this.http.get<CategoriaModel[]>('api/itens/categorias');
    }

    buscarTodos () {
      return this.http.get<ItemModel[]>(`api/itens`);
    }

    buscarPorId (idItem) {
      return this.http.get<ItemModel>(`api/itens/${idItem}`);
    }

    buscarPorUsuario(idUsuario: number){
        return this.http.get<ItemModel[]>(`api/itens/usuario/${idUsuario}`);
    }

    buscarPorSituacao (situacao: boolean) {
        return this.http.get<ItemModel[]>(`api/itens/disponibilidade/${situacao}`);
    }

    salvar (item) {
        return this.http.post(`api/itens`, item);
    }

    atualizar (item) {
      return this.http.put(`api/itens`, item);
    }

    excluir (idItem) {
      return this.http.delete(`api/itens/${idItem}`);
    }
}
