import { Component, OnInit } from '@angular/core';
import { BookService } from '../../../../core/book.service';
import { Book, IBookList } from '../../../../models/book';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CartService } from '../../../../core/cart.service';
import { ToastService } from '../../../../core/toast.service';

@Component({
  selector: 'app-best-selling',
  imports: [CommonModule, RouterLink],
  templateUrl: './best-selling.component.html',
  styleUrl: './best-selling.component.css'
})
export class BestSellingComponent implements OnInit{

  bookList : IBookList[] = [];

  constructor(private bookService : BookService, private cartService : CartService, private toastService : ToastService){

  }

  ngOnInit(): void {
    this.topSelling();
  }

  topSelling(){
    debugger;
    this.bookService.topSelling().subscribe((res : any) => {
      this.bookList = res.data;
    })
  }
    getStars(rating: number): number[] {
    return [1, 2, 3, 4, 5];
  }
   addToCart(item: Book) {
    let cart = this.cartService.getLocalCart();
    const existingItem = cart.find((i: any) => i.bookId === item.id);
  
    if (existingItem) {
      this.toastService.showToast('Sản phẩm đã có trong giỏ hàng!');
      return;
    }
  
    const cartItem = {
      bookId: item.id,
      title: item.title,
      imageUrl: item.imageUrl,
      price: item.price - (item.price * (item.promotion / 100)),
      quantity: 1,
      stock: item.stock
    };
  
    cart.push(cartItem);
    this.cartService.saveLocalCart(cart);
    this.toastService.showToast('Thêm sản phẩm vào giỏ hàng thành công!');
  }
}
