import { OfertaModel } from './../models/oferta-model';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from './../../environments/environment.prod';

@Injectable({
    providedIn: 'root'
})
export class OfertaService {

    private api = environment.apiUrl;

    constructor(private http: HttpClient) { }

    buscarTodos () {
    return this.http.get<OfertaModel[]>(`api/ofertas`);
    }

    buscarPorId (idOferta) {
    return this.http.get<OfertaModel>(`api/ofertas/${idOferta}`);
    }

    salvar (oferta) {
        return this.http.post(`api/ofertas`, oferta);
    }

    atualizar (oferta) {
    return this.http.put(`api/ofertas`, oferta);
    }

    excluir (idOferta) {
    return this.http.delete(`api/ofertas/${idOferta}`);
    }
}
