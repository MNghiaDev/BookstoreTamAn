import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  private readonly TOKEN_KEY = 'access_token';
  constructor(){

  }
  getToken():string | null{
      return localStorage.getItem(this.TOKEN_KEY);
  }
  setToken(token : string):void{
      localStorage.setItem(this.TOKEN_KEY, token);
  }
  removeToken():void{
      localStorage.removeItem(this.TOKEN_KEY);
  }
  isLoggedIn(): boolean {
    return !!localStorage.getItem('access_token');
  }
   isTokenExpired(): boolean {
    const token = this.getToken();
    if (!token) return true;

    try {
      const decoded: any = jwtDecode(token);
      const now = Math.floor(Date.now() / 1000); // Unix timestamp
      return decoded.exp && decoded.exp < now;
    } catch {
      return true;
    }
  }
}
