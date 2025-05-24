import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  apiUrl : string = "http://localhost:8080/api/bookstore/auth";

  constructor(private http : HttpClient) { }

  login(loginObj : any){
    return this.http.post(this.apiUrl + '/token', loginObj);
  }

  register(userObj : User){
    return this.http.post("http://localhost:8080/api/bookstore/user/register" , userObj);
  }

  fetchUsers(page : number, size : number) {
    return this.http.get<any>(`http://localhost:8080/api/bookstore/user/list?page=${page}&size=${size}`)
  }

  deleteUser(id: number) {
      return this.http.delete(`http://localhost:8080/api/bookstore/user/${id}`);
    
    }
}
