import { Component, OnInit } from '@angular/core';
import { BookService } from '../../../../services/book/book.service';
import { IBookList } from '../../../../models/book';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-best-selling',
  imports: [CommonModule, RouterLink],
  templateUrl: './best-selling.component.html',
  styleUrl: './best-selling.component.css'
})
export class BestSellingComponent implements OnInit{

  bookList : IBookList[] = [];

  constructor(private bookService : BookService){

  }

  ngOnInit(): void {
    this.topSelling();
  }

  topSelling(){
    debugger;
    this.bookService.topSelling().subscribe((res : any) => {
      this.bookList = res.data;
    })
  }

}
