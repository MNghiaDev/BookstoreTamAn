import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private apiUrl = 'http://localhost:8080/api/bookstore/order';

  constructor(private http: HttpClient) {}

  createOrder(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/create`, data);
  }
  

  getUserOrders(userId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/user?userId=${userId}`);
  }
}
