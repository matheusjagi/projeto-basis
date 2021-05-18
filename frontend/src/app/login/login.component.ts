import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { finalize } from 'rxjs/operators';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: FormGroup;
  submit = false;
  
  constructor(
    private fb: FormBuilder,
    private loginService: LoginService,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.iniciarForm();
  }

  iniciarForm () {
    this.form = this.fb.group({
      email: [null, [Validators.required, Validators.email]],
      token: [null, [Validators.required]],    
    });
  }

  logar(){
    this.submit = true;
    this.loginService.login(this.form.value).pipe(
      finalize(() => {
        this.submit = false;
        this.form.reset();
      })
    ).subscribe(
      (data) => {
        localStorage.setItem('token', this.form.get('token').value);
        localStorage.setItem('usuario', JSON.stringify(data));
        this.router.navigate(['admin']);
      },
      () => {
        //fazer erro
      }
    )
  }
}
