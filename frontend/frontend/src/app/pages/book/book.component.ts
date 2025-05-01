import { Component, inject, Inject, OnInit } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { FooterComponent } from "../footer/footer.component";
import { Book, IBookList } from '../../models/book';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CategoryComponent } from "../category/category.component";
import { TopAuthorComponent } from "../top-author/top-author.component";
import { BookService } from '../../services/book.service';
import { FormsModule } from '@angular/forms';
import { ToastService } from '../../services/toast.service';
import { CartService } from '../../services/cart.service';
import { AuthorService } from '../../services/author.service';
import { TokenService } from '../../services/token.service';
import { jwtDecode } from 'jwt-decode';

@Component({
  selector: 'app-book',
  imports: [CommonModule, RouterLink, CategoryComponent, TopAuthorComponent, FormsModule, HeaderComponent, FooterComponent],
  templateUrl: './book.component.html',
  styleUrl: './book.component.css'
})
export class BookComponent implements OnInit {
  bookList: IBookList[] = [];
  book: Book = new Book();

  currentPage: number = 0;
  totalPages: number = 0;
  size: number = 8;
  searchKeyword: string = '';
  previewPage(){
    this.currentPage = this.currentPage - 1;
  }
  nextPage(){
    this.currentPage = this.currentPage + 1;
  }

  constructor(private bookService: BookService, private toastService : ToastService
    , private cartService : CartService, private tokenService : TokenService
  ) {}

  ngOnInit(): void {
    this.loadPage();
    this.getBookMaxSale();
  }

  loadPage(): void {
    if (this.searchKeyword.trim() !== '') {
      // Nếu đang có từ khóa -> gọi search
      this.bookService.searchBooks(this.searchKeyword, this.currentPage, this.size).subscribe((res: any) => {
        this.bookList = res.content;
        this.totalPages = res.totalPages;
      });
    } else {
      // Nếu không có từ khóa -> gọi list bình thường
      this.bookService.listBook(this.currentPage, this.size).subscribe((res: any) => {
        this.bookList = res.data.productResponses;
        this.totalPages = res.data.totalPages;
      });
    }
  }


  getBookMaxSale(): void {
    this.bookService.bookMaxSale().subscribe((res: any) => {
      this.book = res.data;
    });
  }

  onSearch(): void {
    this.currentPage = 0; // Khi tìm kiếm reset về trang 0
    this.loadPage();
  }
  
  onClearSearch(): void {
    this.searchKeyword = '';
    this.currentPage = 0;
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
    this.searchKeyword = suggestion;   // chỉ thay đổi ô input
    this.suggestions = [];              // ẩn gợi ý
    // KHÔNG gọi onSearch() luôn
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
  
    if (this.tokenService.isLoggedIn()) {
      const token = this.tokenService.getToken();
      if (token) {
        const tokenDecoded: any = jwtDecode(token);
        const userId = tokenDecoded.user;
  
        this.cartService.addCartItemToBackend(userId, item.id, 1).subscribe({
          next: (res) => {
            const updatedCart = this.cartService.getLocalCart();
            const addedItem = updatedCart.find(i => i.bookId === item.id);
        
            if (addedItem && res?.data?.cartItemId) {
              addedItem.cartItemId = res.data.cartItemId; // ✅ Dùng đúng tên trường
              this.cartService.saveLocalCart(updatedCart);
            }
        
            this.toastService.showToast('Thêm sản phẩm vào giỏ hàng thành công!');
          },
          error: () => {
            this.toastService.showToast('Đã xảy ra lỗi khi lưu vào server!');
          }
        });
        
      }
    } else {
      this.toastService.showToast('Thêm sản phẩm vào giỏ hàng thành công!');
    }
  }
  
}
