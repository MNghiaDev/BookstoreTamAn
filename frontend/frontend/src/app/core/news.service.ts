import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

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
}
