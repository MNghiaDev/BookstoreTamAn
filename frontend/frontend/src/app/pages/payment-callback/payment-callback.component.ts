import { NgIf } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-payment-callback',
  imports: [NgIf],
  templateUrl: './payment-callback.component.html',
  styleUrl: './payment-callback.component.css'
})
export class PaymentCallbackComponent implements OnInit{
  status: string = '';
  constructor(private route: ActivatedRoute, private http: HttpClient, private router: Router) {}

  private apiUrl = environment.apiUrl;

  ngOnInit(): void {
this.route.queryParams.subscribe(params => {
    const responseCode = params['vnp_ResponseCode'];
    const transactionStatus = params['vnp_TransactionStatus'];
    const orderId = params['orderId'];

    if (responseCode === '00' && transactionStatus === '00') {
      // Gọi backend xác nhận thanh toán
      this.http.put(`${this.apiUrl}/payment/confirm-success/${orderId}`, {})
        .subscribe(() => {
          const selectedItems = JSON.parse(localStorage.getItem('checkoutItems') || '[]');
          const currentCart = JSON.parse(localStorage.getItem('cartItems') || '[]');
          const updatedCart = currentCart.filter((cartItem: any) =>
            !selectedItems.some((selectedItem: any) => selectedItem.bookId === cartItem.bookId)
          );
          localStorage.setItem('cartItems', JSON.stringify(updatedCart));
          
          // Xóa checkoutItems sau khi cập nhật giỏ hàng
          localStorage.removeItem('checkoutItems');

          this.router.navigate(['/thank-you']);
        });
    } else {
      this.status = 'failed';
    }
  });
  }
}
