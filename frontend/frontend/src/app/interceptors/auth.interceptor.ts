import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { TokenService } from '../core/token.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private tokenService: TokenService, private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.tokenService.getToken();

    // ✅ Nếu token hết hạn, tự logout
    if (token && this.tokenService.isTokenExpired()) {
      alert('Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại.');
      this.tokenService.removeToken();
      this.router.navigate(['/login']);
      return throwError(() => new Error('Token expired'));
    }

    // ✅ Nếu có token, thêm vào headers
    const authReq = token
      ? req.clone({
          setHeaders: {
            Authorization: `Bearer ${token}`
          }
        })
      : req;

    return next.handle(authReq).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          alert('Bạn không được phép truy cập. Đăng nhập lại!');
          this.tokenService.removeToken();
          this.router.navigate(['/login']);
        }
        return throwError(() => error);
      })
    );
  }
}
