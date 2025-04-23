import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  apiUrl : string = "http://localhost:8080/api/bookstore/author";

  constructor(private http : HttpClient) { }

  topAuthor(){
    return this.http.get(this.apiUrl + "/top-authors");
  }

}
