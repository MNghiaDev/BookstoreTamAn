import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Author } from '../models/author';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  private apiUrl = `${environment.apiUrl}/author`

  // apiUrl : string = "http://localhost:8080/api/bookstore/author";

  constructor(private http : HttpClient) { }

  topAuthor(){
    return this.http.get(this.apiUrl + "/top-authors");
  }

  getAll(page: number, size : number){
    return this.http.get(`${this.apiUrl}/list?page=${page}&size=${size}`);
  }
  getAuthorById(id : any){
    return this.http.get(`${this.apiUrl}/${id}`);
  }
  getAuthorByName(name : string){
    return this.http.get(`${this.apiUrl}/detail/${name}`);
  }

  deleteAuthor(id : number){
    return this.http.delete(`${this.apiUrl}/delete/${id}`);
  }
  dupdateAuthor(id : number, author : Author){
    return this.http.put(`${this.apiUrl}/delete/${id}`, author);
  }
  addAuthor(author : Author){
    return this.http.post(`${this.apiUrl}/create` , author);
  }
}
