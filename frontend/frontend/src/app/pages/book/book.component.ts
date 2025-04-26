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

@Component({
  selector: 'app-book',
  imports: [HeaderComponent, FooterComponent, CommonModule, RouterLink, CategoryComponent, TopAuthorComponent, FormsModule],
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

  constructor(private bookService: BookService) {}

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
  
}
