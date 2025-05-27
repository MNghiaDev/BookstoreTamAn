import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { TokenService } from './token.service';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators'; // ✅ đúng import map
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private tokenService: TokenService, private router: Router, private http: HttpClient) {}

  private apiUrl = environment.apiUrl;

  canActivate(): Observable<boolean> {
    const token = this.tokenService.getToken();

    if (!token || this.tokenService.isTokenExpired()) {
      alert('Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại.');
      this.tokenService.removeToken();
      this.router.navigate(['/login']);
      return of(false);
    }

    return this.http.post<{ data: { valid: boolean } }>(
     `${this.apiUrl}/auth/inspect`,
      { token }
    ).pipe(
      map((res) => {
        if (res.data.valid) {
          return true;
        } else {
          alert('Phiên đăng nhập không hợp lệ. Vui lòng đăng nhập lại.');
          this.tokenService.removeToken();
          this.router.navigate(['/login']);
          return false;
        }
      }),
      catchError(() => {
        alert('Đã xảy ra lỗi xác thực. Vui lòng đăng nhập lại.');
        this.tokenService.removeToken();
        this.router.navigate(['/login']);
        return of(false);
      })
    );
  }
}
