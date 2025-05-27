import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = `${environment.apiUrl}/user`;
  // apiUrl : string = "http://localhost:8080/api/bookstore/auth";

  constructor(private http : HttpClient) { }

  // this.apiUrl + '/token'
  login(loginObj : any){
    return this.http.post( `${environment.apiUrl}/auth/token`, loginObj);
  }
  // "http://localhost:8080/api/bookstore/user/register"
  register(userObj : User){
    return this.http.post( `${this.apiUrl}/register`, userObj);
  }

  // `http://localhost:8080/api/bookstore/user/list?page=${page}&size=${size}`
  fetchUsers(page : number, size : number) {
    return this.http.get<any>(`${this.apiUrl}/list?page=${page}&size=${size}`)
  }

  // `http://localhost:8080/api/bookstore/user/${id}`
  deleteUser(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
