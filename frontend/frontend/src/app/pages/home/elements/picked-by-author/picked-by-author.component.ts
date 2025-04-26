import { Component, OnInit, AfterViewInit } from '@angular/core';
import $ from 'jquery';
import { BookService } from '../../../../services/book.service';
import { CommonModule, NgFor } from '@angular/common';
import { AuthorService } from '../../../../services/author.service';

@Component({
  selector: 'app-picked-by-author',
  imports: [NgFor , CommonModule],
  templateUrl: './picked-by-author.component.html',
  styleUrls: ['./picked-by-author.component.css']
})
export class PickedByAuthorComponent implements OnInit {
  listTopBooksByTopAuthors: any[] = [];

  listTopAuthor : any[] = [];
  carouselInitialized = false;

  constructor(private bookService: BookService, private authorService : AuthorService) {}

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
}
