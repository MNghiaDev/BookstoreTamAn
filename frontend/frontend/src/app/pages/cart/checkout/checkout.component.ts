import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { TokenService } from '../../../core/token.service';
import { OrderService } from '../../../core/order.service';
import { jwtDecode } from 'jwt-decode';
import { FormsModule, NgModel } from '@angular/forms';
import { HeaderComponent } from "../../../layout/header/header.component";
import { FooterComponent } from "../../../layout/footer/footer.component";
import { CommonModule, NgIf } from '@angular/common';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css',
  standalone: true,
  imports: [FormsModule, HeaderComponent, FooterComponent, NgIf, CommonModule, RouterLink]
})
export class CheckoutComponent implements OnInit{
  orderInfo = {
    recipientName: '',
    email: '',
    phone: '',
    shippingAddress: '',
    paymentMethod : ''
  };
  cartItems: any[] = [];
  totalPrice: number = 0;
  constructor(
    private orderService: OrderService,
    private tokenService: TokenService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cartItems = JSON.parse(localStorage.getItem('checkoutItems') || '[]');
    this.calculateTotalPrice();
  }
  calculateTotalPrice() {
    this.totalPrice = this.cartItems.reduce(
      (total, item) => total + item.price * item.quantity,
      0
    );
  }

  submitOrder() {
  const token = this.tokenService.getToken();
  if (!token) {
    this.router.navigateByUrl("/login");
    return;
  }

  const tokenDecoded: any = jwtDecode(token);
  const userId = tokenDecoded.user;
  const selectedItems = JSON.parse(localStorage.getItem('checkoutItems') || '[]');

  const orderData = {
    userId,
    ...this.orderInfo,
    items: selectedItems.map((item: any) => ({
      bookId: item.bookId,
      quantity: item.quantity
    }))
  };

  // Nếu chọn VNPay
    if (this.orderInfo.paymentMethod === 'VNPAY') {
      debugger;
      this.orderService.createVNPayOrder(orderData).subscribe((res: any) => {
        debugger;
        if (res && res.paymentUrl) {
          debugger;
          window.location.href = res.paymentUrl;
          debugger;
        }
      });
    } else {
      // Nếu là COD
      this.orderService.createOrder(orderData).subscribe(() => {
        const currentCart = JSON.parse(localStorage.getItem('cartItems') || '[]');
        const updatedCart = currentCart.filter((cartItem: any) =>
          !selectedItems.some((selectedItem: any) => selectedItem.bookId === cartItem.bookId)
        );
        localStorage.setItem('cartItems', JSON.stringify(updatedCart));
        localStorage.removeItem('checkoutItems');
        this.router.navigate(['/thank-you']);
      });
    }
  }

}
