import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin.component';
import { BreadcrumbModule, MenuModule, PageNotificationModule } from '@nuvem/primeng-components';
import { SharedModule } from '../shared/shared.module';
import { VersionTagModule } from '@nuvem/angular-base';
import { AppTopbarComponent } from '../components/topbar/app.topbar.component';
import { AppFooterComponent } from '../components/footer/app.footer.component';
import { BlockUIModule } from 'ng-block-ui';
import { ProgressSpinnerModule } from 'primeng';

@NgModule({
    declarations: [
        AdminComponent,
        AppTopbarComponent,
        AppFooterComponent
    ],
    imports: [
        CommonModule,
        AdminRoutingModule,
        SharedModule,
        BreadcrumbModule,
        MenuModule,
        VersionTagModule,
        PageNotificationModule,
        BlockUIModule,
        ProgressSpinnerModule
    ]
})
export class AdminModule { }
