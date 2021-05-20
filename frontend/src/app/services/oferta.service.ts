import { OfertaModule } from './../oferta/oferta.module';
import { OfertaModel } from './../models/oferta-model';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from './../../environments/environment.prod';

@Injectable({
    providedIn: 'root'
})
export class OfertaService {

    private api: string = environment.apiUrl + "/ofertas";

    constructor(private http: HttpClient) { }

    aceitarOferta(idOferta){
        return this.http.patch(`${this.api}/aceitar/${idOferta}`, {});
    }

    buscarTodos() {
        return this.http.get<OfertaModel[]>(`${this.api}`);
    }

    buscarPorId(idOferta) {
        return this.http.get<OfertaModel>(`${this.api}/${idOferta}`);
    }

    buscarPorItem(idItem) {
        return this.http.get<OfertaModule[]>(`${this.api}/item/${idItem}`);
    }

    salvar(oferta) {
        return this.http.post(`${this.api}`, oferta);
    }

    atualizar(oferta) {
        return this.http.put(`${this.api}`, oferta);
    }

    excluir(idOferta) {
        return this.http.delete(`${this.api}/${idOferta}`);
    }
}
