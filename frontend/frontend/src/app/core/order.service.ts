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

  createVNPayOrder(orderData: any) {
    return this.http.post<any>('http://localhost:8080/api/bookstore/payment/vnpay-order', orderData);
  }

  myOrder(page : number, size : number){
    const token = localStorage.getItem('access_token');
    return this.http.get(`${this.apiUrl}/my-orders?page=${page}&size=${size}`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
  }
  orderDetail(id : number){
    return this.http.get(`${this.apiUrl}/my-orders/${id}`);
  }
  confirmOrderReceived(id : number){
    return this.http.get(`${this.apiUrl}/confirm-received/${id}`);
  }

  cancelOrderReceived(id : number){
    return this.http.get(`${this.apiUrl}/canceled-order/${id}`);
  }
}
