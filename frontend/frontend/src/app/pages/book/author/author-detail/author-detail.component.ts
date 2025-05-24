import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { AuthorService } from '../../../../core/author.service';
import { Author } from '../../../../models/author';
import { HeaderComponent } from "../../../../layout/header/header.component";
import { FooterComponent } from "../../../../layout/footer/footer.component";
import { BookService } from '../../../../core/book.service';
import { Book, IBookList } from '../../../../models/book';
import { CommonModule } from '@angular/common';
import { CartService } from '../../../../core/cart.service';
import { ToastService } from '../../../../core/toast.service';

@Component({
  selector: 'app-author-detail',
  imports: [HeaderComponent, FooterComponent, CommonModule, RouterLink],
  templateUrl: './author-detail.component.html',
  styleUrl: './author-detail.component.css'
})
export class AuthorDetailComponent implements OnInit{
  authorId! : number;
  author : Author = new Author();
  bookList : IBookList[] = [];

  page: number = 0;
  totalPages: number = 0;
  size: number = 18;

  totalBooks : number = 0;



  constructor(private route: ActivatedRoute, private authorService : AuthorService, private bookService : BookService,
    private cartService : CartService, private toastService : ToastService
  ){}
  ngOnInit(): void {
    this.authorId = Number(this.route.snapshot.paramMap.get('id'));
    this.onAuthorDetail();
    this.onSelectBookByAuthor();
  }

  onAuthorDetail(){
    this.authorService.getAuthorById(this.authorId).subscribe((res : any) => {
      this.author = res.data;
    })
  }
  onSelectBookByAuthor(){
    this.bookService.bookByAuthor(this.authorId, this.page, this.size).subscribe((res : any) => {
      this.bookList = res.data.productResponses;
    })
  }
    nextPage() {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.onSelectBookByAuthor();
    }
  }

  previousPage() {
    if (this.page > 0) {
      this.page--;
      this.onSelectBookByAuthor();
    }
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
