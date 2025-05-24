import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Category } from '../models/category';

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

  listCategory(page: number, size : number){
    return this.http.get(`${this.apiUrl}/list?page=${page}&size=${size}`);
  }
  getCategoryById(categoryId : number){
    return this.http.get(`${this.apiUrl}/${categoryId}`);
  }
  getCategoryByName(name : string){
    return this.http.get(`${this.apiUrl}/detail/${name}`);
  }

  createCategory(category : Category){
    return this.http.post(`${this.apiUrl}/create`, category);
  }

  updateCategory(id : number , category : Category){
    return this.http.put(`${this.apiUrl}/update/${id}`, category);
  }

  deleteCategory(id : number){
    return this.http.delete(`${this.apiUrl}/delete/${id}`);
  }
}
