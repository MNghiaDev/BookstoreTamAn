import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { TokenService } from './token.service';
import { jwtDecode } from 'jwt-decode';


@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private tokenService: TokenService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const token = this.tokenService.getToken();
    
    if (!token) {
      this.router.navigateByUrl('/login');
      return false;
    }

    try {
      const tokenDecoded: any = jwtDecode(token);
      const scopes = tokenDecoded.scope;
      
      // Chỉ cho phép admin truy cập
      if (scopes.includes("ROLE_admin")) {
        return true;
      } else {
        alert('Bạn không có quyền truy cập vào trang admin.');
        this.router.navigateByUrl('/home');
        return false;
      }
    } catch (error) {
      this.router.navigateByUrl('/login');
      return false;
    }
  }
}
