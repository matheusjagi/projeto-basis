import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LOCALE_ID, NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SharedModule } from './shared/shared.module';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { environment } from '../environments/environment';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { PageNotificationModule, BreadcrumbModule, MenuModule, ErrorStackModule } from '@nuvem/primeng-components';
import { ErrorModule, SecurityModule, VersionTagModule } from '@nuvem/angular-base';
import { DiarioErrosComponent } from './components/diario-erros/diario-erros.component';
import { BlockUIModule } from 'ng-block-ui';
import { LoginComponent } from './login/login.component';
import { ReactiveFormsModule } from '@angular/forms';
import { TokenInterceptor } from './interceptors/token.interceptor';

@NgModule({
    declarations: [
        AppComponent,
        DiarioErrosComponent,
        LoginComponent
    ],
    imports: [
        BlockUIModule.forRoot({
            message: "Carregando..."
          }),
        BrowserModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        SharedModule,
        HttpClientModule,
        PageNotificationModule,
        BreadcrumbModule,
        ErrorStackModule,
        ErrorModule,
        VersionTagModule,
        SecurityModule.forRoot(environment.auth),
        MenuModule,
        ReactiveFormsModule
    ],
    providers: [
        { provide: LocationStrategy, useClass: HashLocationStrategy },
        { provide: LOCALE_ID, useValue: 'pt-BR'},
        { provide: HTTP_INTERCEPTORS, multi: true, useClass: TokenInterceptor}
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }