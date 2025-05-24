import { Component, inject, Inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Route, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { jwtDecode } from 'jwt-decode';
import { CategoryComponent } from '../share/category/category.component';
import { TopAuthorComponent } from '../share/top-author/top-author.component';
import { HeaderComponent } from '../../../layout/header/header.component';
import { FooterComponent } from '../../../layout/footer/footer.component';
import { Book, IBookList } from '../../../models/book';
import { BookService } from '../../../core/book.service';
import { ToastService } from '../../../core/toast.service';
import { TokenService } from '../../../core/token.service';
import { CartService } from '../../../core/cart.service';
import { NewsComponent } from "../share/news/news.component";
import { AuthorService } from '../../../core/author.service';
import { CategoryService } from '../../../core/category.service';

@Component({
  selector: 'app-book',
  imports: [CommonModule, RouterLink, CategoryComponent, TopAuthorComponent, 
    FormsModule, HeaderComponent, FooterComponent, NewsComponent],
  templateUrl: './book.component.html',
  styleUrl: './book.component.css'
})
export class BookComponent implements OnInit {
  bookList: IBookList[] = [];
  book: Book = new Book();

  page: number = 0;
  totalPages: number = 0;
  size: number = 8;
  searchKeyword: string = '';

  nextPage() {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.loadPage();
    }
  }

  previousPage() {
    if (this.page > 0) {
      this.page--;
      this.loadPage();
    }
  }

  constructor(private bookService: BookService, private toastService : ToastService
    , private cartService : CartService, private tokenService : TokenService,
    private route: ActivatedRoute, private authorService : AuthorService, private categoryService : CategoryService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.searchKeyword = params['keyword'] || '';
      this.page = 0;
      this.loadPage();
    });

    this.getBookMaxSale();
  }
 loadPage(): void {
  if (this.searchKeyword.trim() !== '' && this.minPrice !== null && this.maxPrice !== null) {
    this.bookService.filterByPriceAndKeyword(this.searchKeyword, this.minPrice, this.maxPrice, this.page, this.size)
      .subscribe((res: any) => {
        this.bookList = res.data.productResponses;
        this.totalPages = res.data.totalPages;
      });
  } else if (this.minPrice !== null && this.maxPrice !== null) {
    this.bookService.filterByPrice(this.minPrice, this.maxPrice, this.page, this.size)
      .subscribe((res: any) => {
        this.bookList = res.data.productResponses;
        this.totalPages = res.data.totalPages;
      });
  } else if (this.searchKeyword.trim() !== '') {
    this.bookService.searchBooks(this.searchKeyword, this.page, this.size)
      .subscribe((res: any) => {
        this.bookList = res.content;
        this.totalPages = res.totalPages;
      });
  } else {
    this.bookService.listBook(this.page, this.size)
      .subscribe((res: any) => {
        this.bookList = res.data.productResponses;
        this.totalPages = res.data.totalPages;
      });
  }
}


  onSearch(): void {
  this.minPrice = null;
  this.maxPrice = null;
  this.page = 0;
  this.loadPage();
}


  getBookMaxSale(): void {
    this.bookService.bookMaxSale().subscribe((res: any) => {
      this.book = res.data;
    });
  }

  
  onClearSearch(): void {
    this.searchKeyword = '';
    this.page = 0;
    this.loadPage();
  }

  suggestions: string[] = [];

  onKeywordChange(): void {
    if (this.searchKeyword.trim() === '') {
      this.suggestions = [];
      return;
    }
  
    this.bookService.getSuggestions(this.searchKeyword).subscribe((res: any) => {
      this.suggestions = res.data || [];
    });
  }
  
  selectSuggestion(suggestion: string): void {
    this.searchKeyword = suggestion;
    this.suggestions = [];
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
  minPrice: number | null = null;
  maxPrice: number | null = null;

  applyPriceFilter(): void {
    this.page = 0;
    this.loadPage();
  }
selectPriceRange(event: Event) {
  const target = event.target as HTMLSelectElement;
  const value = target.value;

  if (value) {
    const [min, max] = value.split('-').map(Number);
    this.minPrice = min;
    this.maxPrice = max;
  } else {
    this.minPrice = null;
    this.maxPrice = null;
  }

    this.page = 0;
    this.loadPage(); // ✅ Tự động gọi loadPage khi chọn giá
  }

  getStars(rating: number): number[] {
  return [1, 2, 3, 4, 5];
}
}
