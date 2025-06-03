import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { News } from '../models/news';

@Injectable({
  providedIn: 'root'
})
export class NewsService {

    private apiUrl = `${environment.apiUrl}/news`;
  // apiUrl : string = "http://localhost:8080/api/bookstore/news";
  constructor(private http : HttpClient) { }

  getList(page : number , size : number){
    return this.http.get(`${this.apiUrl}/list?page=${page}&size=${size}`);
  }

  getById(id : number){
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  create(news : News){
    return this.http.post(`${this.apiUrl}/create`, news);
  }

  update(id : number , news : News){
    return this.http.put(`${this.apiUrl}/update/${id}`, news);
  }

  delete(id : number){
    return this.http.delete(`${this.apiUrl}/delete/${id}`);
  }
}
