import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from '../../services/token.service';
import { OrderService } from '../../services/order.service';
import { jwtDecode } from 'jwt-decode';
import { FormsModule, NgModel } from '@angular/forms';
import { HeaderComponent } from "../header/header.component";
import { FooterComponent } from "../footer/footer.component";

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css',
  standalone: true,
  imports: [FormsModule, HeaderComponent, FooterComponent]
})
export class CheckoutComponent {
  orderInfo = {
    recipientName: '',
    email: '',
    phone: '',
    shippingAddress: '',
    paymentMethod : ''
  };

  constructor(
    private orderService: OrderService,
    private tokenService: TokenService,
    private router: Router
  ) {}

  submitOrder() {
    const token = this.tokenService.getToken();
    if (!token){
      this.router.navigateByUrl("/login");
    }else{
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
        
        this.orderService.createOrder(orderData).subscribe(() => {
            // Xóa các sản phẩm đã đặt ra khỏi giỏ hàng
            const currentCart = JSON.parse(localStorage.getItem('cartItems') || '[]');
            const updatedCart = currentCart.filter((cartItem: any) => 
                !selectedItems.some((selectedItem: any) => selectedItem.bookId === cartItem.bookId)
            );
            localStorage.setItem('cartItems', JSON.stringify(updatedCart));
            
            // Xóa sản phẩm đang checkout
            localStorage.removeItem('checkoutItems');
            
            // Điều hướng tới trang cảm ơn
            this.router.navigate(['/thank-you']);
        });
    }
  }
}
