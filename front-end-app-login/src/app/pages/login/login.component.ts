import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { DefaultLoginLayoutComponent } from 'src/app/components/default-login-layout/default-login-layout.component';


@Component({
  selector: 'app-login',
  standalone: true,
  imports:[CommonModule ,DefaultLoginLayoutComponent],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

}
