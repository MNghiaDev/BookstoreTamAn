import { Component } from '@angular/core';
import { CartService } from '../../services/cart.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ToastService } from '../../services/toast.service';
import { AuthorService } from '../../services/author.service';
import { TokenService } from '../../services/token.service';
import { jwtDecode } from 'jwt-decode';
import { HeaderComponent } from "../header/header.component";
import { FooterComponent } from "../footer/footer.component";


@Component({
  selector: 'app-cart',
  imports: [CommonModule, FormsModule, HeaderComponent, FooterComponent],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent {
  cartItems: any[] = [];
  totalPrice: number = 0;

  constructor(
    private cartService: CartService,
    private toastService: ToastService,
    private tokenService : TokenService
  ) {}

  ngOnInit(): void {
    this.loadCart();
  }

  loadCart() {
    this.cartItems = this.cartService.getLocalCart();
    this.calculateTotalPrice();
  }

  calculateTotalPrice() {
    this.totalPrice = this.cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);
  }

  increaseQuantity(item: any) {
    if (item.quantity < item.stock) {
      item.quantity++;
      this.saveCart(item);
    } else {
      this.toastService.showToast('Số lượng đạt tối đa trong kho!');
    }
  }

  decreaseQuantity(item: any) {
    if (item.quantity > 1) {
      item.quantity--;
      this.saveCart(item);
    }
  }

  changeQuantity(item: any, event: any) {
    const newQuantity = parseInt(event.target.value, 10);
    if (newQuantity <= 0) {
      event.target.value = item.quantity; // reset
      return;
    }
    if (newQuantity > item.stock) {
      this.toastService.showToast('Vượt quá số lượng tồn kho!');
      item.quantity = item.stock;
    } else {
      item.quantity = newQuantity;
    }
    this.saveCart(item);
  }

  saveCart(item: any) {
    this.cartService.saveLocalCart(this.cartItems);
    this.calculateTotalPrice();

    // Nếu đã login, update database luôn
    if (this.tokenService.isLoggedIn()) {
      this.cartService.updateCartItemBackend(item.cartItemId, item.quantity).subscribe();
    }
  }

  removeItem(item: any) {
    if (confirm('Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng?')) {
      this.cartItems = this.cartItems.filter(i => i.bookId !== item.bookId);
      this.cartService.saveLocalCart(this.cartItems);

      if (this.tokenService.isLoggedIn()) {
        this.cartService.removeCartItemBackend(item.cartItemId).subscribe();
      }

      this.calculateTotalPrice();
      this.toastService.showToast('Đã xóa sản phẩm khỏi giỏ hàng');
    }
  }

  clearCart() {
    if (confirm('Bạn có chắc chắn muốn xóa toàn bộ giỏ hàng?')) {
      this.cartItems = [];
      this.cartService.clearLocalCart();
      this.totalPrice = 0;

      if (this.tokenService.isLoggedIn()) {
        const token = this.tokenService.getToken();
        if(token != null){
          const tokenDecoded : any = jwtDecode(token);
          const userId = tokenDecoded.user;
          this.cartService.clearCartBackend(userId).subscribe();
        }
      }

      this.toastService.showToast('Đã xóa toàn bộ giỏ hàng');
    }
  }
}
