import { Component, OnInit, AfterViewInit } from '@angular/core';
import $ from 'jquery';
import { BookService } from '../../../../core/book.service';
import { CommonModule, NgFor } from '@angular/common';
import { AuthorService } from '../../../../core/author.service';
import { RouterLink } from '@angular/router';
import { Book } from '../../../../models/book';
import { CartService } from '../../../../core/cart.service';
import { ToastService } from '../../../../core/toast.service';

@Component({
  selector: 'app-picked-by-author',
  imports: [NgFor , CommonModule, RouterLink],
  templateUrl: './picked-by-author.component.html',
  styleUrls: ['./picked-by-author.component.css']
})
export class PickedByAuthorComponent implements OnInit {
  listTopBooksByTopAuthors: any[] = [];

  listTopAuthor : any[] = [];
  carouselInitialized = false;

  constructor(private bookService: BookService, private authorService : AuthorService, private cartService : CartService, private toastService : ToastService) {}

  ngOnInit(): void {
    this.carouselInitialized = false;
    this.topBooksByTopAuthors();
    this.topAuthor();
  }


  topBooksByTopAuthors() {
    this.bookService.topBooksByTopAuthors().subscribe((res: any) => {
      this.listTopBooksByTopAuthors = res.data;
    });
  }

  topAuthor(){
    this.authorService.topAuthor().subscribe((res : any) => {
      this.listTopAuthor = res.data;
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
