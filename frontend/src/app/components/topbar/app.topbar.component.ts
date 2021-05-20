import { LocalstorageService } from './../../services/localstorage.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AdminComponent } from 'src/app/admin/admin.component';

@Component({
    selector: 'app-topbar',
    templateUrl: './app.topbar.component.html'
})
export class AppTopbarComponent {

    loginUsuario = this.localstorageService.getNome();

    constructor(
        private localstorageService: LocalstorageService,
        public app: AdminComponent,
        private router: Router
    ) {}

    logout(){
        localStorage.removeItem('token');
        this.router.navigate(['login']);
    }

}
