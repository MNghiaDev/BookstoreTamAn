import { Component, OnInit } from '@angular/core';
import { Book } from '../../../models/book';
import { BookService } from '../../../core/book.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FooterComponent } from "../../../layout/footer/footer.component";
import { HeaderComponent } from "../../../layout/header/header.component";
import { CategoryComponent } from "../share/category/category.component";
import { TopAuthorComponent } from "../share/top-author/top-author.component";
import { NewsComponent } from "../share/news/news.component";
import { AuthorService } from '../../../core/author.service';
import { Author } from '../../../models/author';
import { CartService } from '../../../core/cart.service';
import { ToastService } from '../../../core/toast.service';

@Component({
  selector: 'app-book-detail',
  imports: [FormsModule, CommonModule, FooterComponent, HeaderComponent, CategoryComponent, TopAuthorComponent, NewsComponent, RouterLink],
  templateUrl: './book-detail.component.html',
  styleUrl: './book-detail.component.css'
})
export class BookDetailComponent implements OnInit{

  book : Book = new Book();

  bookId! : number;

  author : Author = new Author();

  authorIdByBook : number = 0;

  constructor(private bookService : BookService, private route: ActivatedRoute, private authorService : AuthorService,
    private cartService : CartService, private toastService : ToastService
  ){
  }

  ngOnInit(): void {
    this.bookId = Number(this.route.snapshot.paramMap.get('id'));
      // this.getAuthor();
    this.loadBook();
  
  }
  loadBook(){
    this.bookService.bookDetail(this.bookId).subscribe((res : any) => {
      this.book = res.data;
      this.authorService.getAuthorById(this.book.authorId).subscribe((res : any) =>{
        this.author = res.data;
      })
    })
  }
  selectQuantity : number = 0;
  buyNow() {

  }

  // getAuthor(){
  //   debugger;
  //   this.authorService.getAuthorById(this.authorIdByBook).subscribe((res : any) => {
  //     debugger;
  //     this.author = res.data;
  //   })
  // }
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
