import { Component } from '@angular/core';
import { Authentication, User } from '@nuvem/angular-base';
import { AdminComponent } from 'src/app/admin/admin.component';

@Component({
    selector: 'app-topbar',
    templateUrl: './app.topbar.component.html'
})
export class AppTopbarComponent {

    constructor(public app: AdminComponent) {
    }

    // get usuario() {
    //     return this._authentication.getUser();
    // }

    // isAuthenticated() {
    //     return this._authentication.isAuthenticated();
    // }
}
