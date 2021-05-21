import { UsuarioModel } from './../models/usuario-model';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class LocalstorageService {

    constructor() { }

    usuarioLogado: UsuarioModel = JSON.parse(localStorage.getItem('usuario'));

    getUsuario(){
        return this.usuarioLogado;
    }

    setUsuario(usuario){
        this.usuarioLogado = usuario;
    }

    getId() {
        return this.usuarioLogado.id;
    }

    getNome() {
        return this.usuarioLogado.nome;
    }

    getEmail() {
        return this.usuarioLogado.email;
    }

    getToken() {
        return this.usuarioLogado.token;
    }
}
