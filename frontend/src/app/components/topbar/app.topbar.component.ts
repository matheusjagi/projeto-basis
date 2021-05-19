import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AdminComponent } from 'src/app/admin/admin.component';

@Component({
    selector: 'app-topbar',
    templateUrl: './app.topbar.component.html'
})
export class AppTopbarComponent {

    loginUsuario = JSON.parse(localStorage.getItem('usuario')).email;

    constructor(public app: AdminComponent, private router: Router) {
    }

    logout(){
        localStorage.removeItem("token");
        this.router.navigate(['login']);
    }

}
