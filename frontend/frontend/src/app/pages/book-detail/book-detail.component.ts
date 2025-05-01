import { Component, OnInit } from '@angular/core';
import { Book } from '../../models/book';
import { BookService } from '../../services/book.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { FooterComponent } from "../footer/footer.component";
import { HeaderComponent } from "../header/header.component";

@Component({
  selector: 'app-book-detail',
  imports: [FormsModule, CommonModule, FooterComponent, HeaderComponent],
  templateUrl: './book-detail.component.html',
  styleUrl: './book-detail.component.css'
})
export class BookDetailComponent implements OnInit{

  book : Book = new Book();

  bookId! : number;

  constructor(private bookService : BookService, private route: ActivatedRoute){
  }

  ngOnInit(): void {
    this.bookId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadBook();
  }
  loadBook(){
    this.bookService.bookDetail(this.bookId).subscribe((res : any) => {
      this.book = res.data;
    })
  }

}
