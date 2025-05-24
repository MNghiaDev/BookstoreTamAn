import { Component, OnInit } from '@angular/core';
import { CommonModule, NgFor } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HeaderComponent } from '../../../layout/header/header.component';
import { FooterComponent } from '../../../layout/footer/footer.component';
import { CartService } from '../../../core/cart.service';
import { ToastService } from '../../../core/toast.service';
import { TokenService } from '../../../core/token.service';

@Component({
  selector: 'app-cart',
  imports: [CommonModule, FormsModule, HeaderComponent, FooterComponent, NgFor, RouterLink],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent implements OnInit{
  cartItems: any[] = [];
  totalPrice: number = 0;

  constructor(
    private cartService: CartService,
    private toastService: ToastService,
    private tokenService: TokenService,
    private router: Router,

  ) {}

  ngOnInit(): void {
    this.loadCart();
  }

  loadCart() {
    this.cartItems = this.cartService.getLocalCart().map(item => ({
      ...item,
      selected: false
    }));
    this.calculateTotalPrice();
  }

  get allSelected(): boolean {
    return this.cartItems.length > 0 && this.cartItems.every(item => item.selected);
  }

  get hasSelectedItems(): boolean {
    return this.cartItems.some(item => item.selected);
  }

  toggleAllSelection() {
    const newValue = !this.allSelected;
    this.cartItems.forEach(i => i.selected = newValue);
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
      event.target.value = item.quantity;
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

    // if (this.tokenService.isLoggedIn() && item.cartItemId) {
    //   this.cartService.updateCartItemBackend(item.cartItemId, item.quantity).subscribe();
    // }
  }

  removeItem(item: any) {
    if (confirm('Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng?')) {
      this.cartItems = this.cartItems.filter(i => i.bookId !== item.bookId);
      this.cartService.saveLocalCart(this.cartItems);

      // if (this.tokenService.isLoggedIn() && item.cartItemId) {
      //   this.cartService.removeCartItemBackend(item.cartItemId).subscribe();
      // }

      this.calculateTotalPrice();
      this.toastService.showToast('Đã xóa sản phẩm khỏi giỏ hàng');
    }
  }

  clearCart() {
    if (confirm('Bạn có chắc chắn muốn xóa toàn bộ giỏ hàng?')) {
      this.cartItems = [];
      this.cartService.clearLocalCart();
      this.totalPrice = 0;

      // if (this.tokenService.isLoggedIn()) {
      //   const token = this.tokenService.getToken();
      //   if (token != null) {
      //     const tokenDecoded: any = jwtDecode(token);
      //     const userId = tokenDecoded.user;
      //     this.cartService.clearCartBackend(userId).subscribe();
      //   }
      // }

      this.toastService.showToast('Đã xóa toàn bộ giỏ hàng');
    }
  }

  checkoutSelected() {
    const selectedItems = this.cartItems.filter(i => i.selected);
    if (selectedItems.length === 0) {
      this.toastService.showToast('Vui lòng chọn ít nhất một sản phẩm để thanh toán.');
      return;
    }
    localStorage.setItem('checkoutItems', JSON.stringify(selectedItems));
    const token = this.tokenService.getToken();
    if (!token){
      this.toastService.showToast('Vui lòng đăng nhập trước khi đặt hàng.');
      this.router.navigateByUrl("/login");
    }else{
    this.router.navigate(['/checkout']);
    }
  }

}
