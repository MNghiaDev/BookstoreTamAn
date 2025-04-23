import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  apiUrl : string = "http://localhost:8080/api/bookstore/category";

  constructor(private http : HttpClient) { }

  listBook(){
    return this.http.get(this.apiUrl);
  }

  countProduct(){
    return this.http.get(this.apiUrl + "/book-counts");
  }

}
