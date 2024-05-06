import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

/* Toda vez que alguém for tentar acessar a rota user a gente vai verificar se o usuario tem
o auth token no session storage*/ 
export class AuthGuard implements CanActivate {

  constructor(private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const authToken = sessionStorage.getItem('auth-token');

    /** Se tiver eu retorno true e ele poderá acessar */
    if (authToken) {
      return true;
      // Se não tiver eu retorno ele para tela de login e retorno false
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
