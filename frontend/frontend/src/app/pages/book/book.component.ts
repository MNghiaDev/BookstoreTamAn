import { Component, inject, Inject, OnInit } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { FooterComponent } from "../footer/footer.component";
import { Book, IBookList } from '../../models/book';
import { HttpClient } from '@angular/common/http';
import { BookService } from '../../services/book/book.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-book',
  imports: [HeaderComponent, FooterComponent, CommonModule, RouterLink],
  templateUrl: './book.component.html',
  styleUrl: './book.component.css'
})
export class BookComponent implements OnInit{
  bookList : IBookList[] = [];

  book : Book = new Book();

  bookSale : Book = new Book();

  page : number = 0;
  size : number = 8;
  currentPage : number = 0;

  http = inject(HttpClient);

  constructor(private bookService : BookService){

  }
  ngOnInit(): void {
    this.getBooks();
    this.getBookMaxSale();
  }
  getBooks(){
    this.bookService.listBook(this.page,this.size).subscribe((res : any) => {
      this.bookList = res.data.productResponses;
      this.page = res.totalPages;
      this.currentPage = this.page;
    })
  }
  getBookMaxSale(){
    this.bookService.bookMaxSale().subscribe((res:any) => {
      debugger;
      this.book = res.data;
    })
  }
}
