import { environment } from './../../environments/environment.prod';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

    private api = environment.apiUrl;

    constructor(private http: HttpClient) { }

    buscarCategorias(){
      return this.http.get<any[]>('api/itens/categorias');
    }

    buscarTodos () {
      return this.http.get<any[]>(`api/itens`);
    }

    buscarPorId (idItem) {
      return this.http.get<any>(`api/itens/${idItem}`);
    }

    buscarPorSituacao (situacao: boolean) {
        return this.http.get<any>(`api/itens/disponibilidade/${situacao}`);
    }

    salvar (item) {
        console.log("entrouuuuuuu: ",item);

        return this.http.post(`api/itens`, item);
    }

    atualizar (item) {
      return this.http.put(`api/itens`, item);
    }

    excluir (idItem) {
      return this.http.delete(`api/itens/${idItem}`);
    }
}
