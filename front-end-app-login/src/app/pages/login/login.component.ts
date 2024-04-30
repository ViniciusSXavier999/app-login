import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { DefaultLoginLayoutComponent } from 'src/app/components/default-login-layout/default-login-layout.component';
import { PrimaryInputComponent } from 'src/app/components/primary-input/primary-input.component';
import { LoginService } from 'src/app/services/login.service';


@Component({
  selector: 'app-login',
  standalone: true,
  imports:[CommonModule ,DefaultLoginLayoutComponent,
    ReactiveFormsModule, PrimaryInputComponent
  ],
  providers: [
    LoginService
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginForm!: FormGroup;

  constructor(
    private router: Router,
    private loginService: LoginService,
    private toastService: ToastrService
  ){
    this.loginForm = new FormGroup({
      // campos que vão ter dentro do meu formulario
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)])
    })
  }

  submit() {
    this.loginService.login(this.loginForm.value.email, this.loginForm.value.password).subscribe({
      next: () => this.toastService.success("Login feito"),
      error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
    })
  }

  navigate() {
    this.router.navigate(["signup"])
  }
}
