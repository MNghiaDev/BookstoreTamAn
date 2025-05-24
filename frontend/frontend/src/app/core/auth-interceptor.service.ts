import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { TokenService } from '../core/token.service';
import { Router } from '@angular/router';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const tokenService = inject(TokenService);
  const router = inject(Router);
  const token = tokenService.getToken();


  if (token && tokenService.isTokenExpired()) {
    alert('Phiên đăng nhập đã hết hạn');
    tokenService.removeToken();
    router.navigate(['/home']);
    return next(req);
  }

  if (token) {
    const clonedRequest = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`)
    });
    return next(clonedRequest);
  }

  return next(req);
};
