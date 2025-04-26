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

@Component({
  selector: 'app-book',
  imports: [HeaderComponent, FooterComponent, CommonModule, RouterLink, CategoryComponent, TopAuthorComponent],
  templateUrl: './book.component.html',
  styleUrl: './book.component.css'
})
export class BookComponent implements OnInit {
  bookList: IBookList[] = [];
  book: Book = new Book();

  currentPage: number = 0;
  totalPages: number = 0;
  size: number = 8;

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
    this.bookService.listBook(this.currentPage, this.size).subscribe((res: any) => {
      this.bookList = res.data.productResponses;
      this.totalPages = res.data.totalPages;
    });
  }


  getBookMaxSale(): void {
    this.bookService.bookMaxSale().subscribe((res: any) => {
      this.book = res.data;
    });
  }
}
