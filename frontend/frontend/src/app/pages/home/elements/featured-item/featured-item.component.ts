import { Component, OnInit } from '@angular/core';
import { Book, IBookList } from '../../../../models/book';
import { BookService } from '../../../../core/book.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CartService } from '../../../../core/cart.service';
import { ToastService } from '../../../../core/toast.service';

@Component({
  selector: 'app-featured-item',
  imports: [CommonModule, RouterLink],
  templateUrl: './featured-item.component.html',
  styleUrl: './featured-item.component.css'
})
export class FeaturedItemComponent implements OnInit{
  book : Book = new Book();

  bookList : IBookList[] = [];

  constructor(private bookService : BookService, private cartService : CartService, private toastService : ToastService){}

  ngOnInit(): void {
    this.getBookFeatured();
    this.getBookByPublicationDate();
  }

  getBookFeatured(){
    this.bookService.bookMaxSale().subscribe((res : any) => {
      this.book = res.data;
    })
  }

  getBookByPublicationDate(){
    this.bookService.bookByPublicationDate().subscribe((res : any) => {
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
      price: item.price,
      quantity: 1,
      stock: item.stock
    };
  
    cart.push(cartItem);
    this.cartService.saveLocalCart(cart);
    this.toastService.showToast('Thêm sản phẩm vào giỏ hàng thành công!');
  }
}
