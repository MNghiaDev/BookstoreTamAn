import { Component, OnInit } from '@angular/core';
import { Book, IBookList } from '../../../../models/book';
import { BookService } from '../../../../services/book.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-featured-item',
  imports: [CommonModule],
  templateUrl: './featured-item.component.html',
  styleUrl: './featured-item.component.css'
})
export class FeaturedItemComponent implements OnInit{
  book : Book = new Book();

  bookList : IBookList[] = [];

  constructor(private bookService : BookService){}

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
}
