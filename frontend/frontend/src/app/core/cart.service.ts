// ✅ FIXED CartService (frontend)
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, forkJoin } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CartService {
  apiUrl: string = "http://localhost:8080/api/bookstore/cart";
  private storageKey = 'cartItems';

  constructor(private http: HttpClient) {}

  getLocalCart(): any[] {
    const cart = localStorage.getItem(this.storageKey);
    return cart ? JSON.parse(cart) : [];
  }

  saveLocalCart(cart: any[]): void {
    localStorage.setItem(this.storageKey, JSON.stringify(cart));
  }

  clearLocalCart(): void {
    localStorage.removeItem(this.storageKey);
  }

  // addCartItemToBackend(userId: number, bookId: number, quantity: number): Observable<any> {
  //   return this.http.post(`${this.apiUrl}/add?userId=${userId}`, { bookId, quantity });
  // }

  // updateCartItemBackend(cartItemId: number, quantity: number): Observable<any> {
  //   return this.http.put(`${this.apiUrl}/update`, { cartItemId, quantity });
  // }

  // removeCartItemBackend(cartItemId: number): Observable<any> {
  //   return this.http.delete(`${this.apiUrl}/delete/${cartItemId}`);
  // }

  // clearCartBackend(userId: number): Observable<any> {
  //   return this.http.delete(`${this.apiUrl}/clear?userId=${userId}`);
  // }

  // syncLocalCartToBackend(userId: number): Observable<any> {
  //   const localCart = this.getLocalCart();
  //   if (localCart.length === 0) return of(null);

  //   const requests = localCart.map(item =>
  //     this.addCartItemToBackend(userId, item.bookId, item.quantity)
  //   );

  //   return forkJoin(requests); // ✅ Sửa để đúng kiểu observable
  // }
}
