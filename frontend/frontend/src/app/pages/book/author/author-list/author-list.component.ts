import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { HeaderComponent } from "../../../../layout/header/header.component";
import { FooterComponent } from "../../../../layout/footer/footer.component";
import { Author, IAuthorList } from '../../../../models/author';
import { AuthorService } from '../../../../core/author.service';

@Component({
  selector: 'app-author-list',
  imports: [RouterOutlet, HeaderComponent, FooterComponent, RouterLink],
  templateUrl: './author-list.component.html',
  styleUrl: './author-list.component.css'
})
export class AuthorListComponent implements OnInit {
  authorList : IAuthorList[] = [];
  author : Author = new Author();

  currentPage: number = 0;
  totalPages: number = 0;
  size: number = 18;

  constructor(private authorService : AuthorService){}

  previewPage(){
    this.currentPage = this.currentPage - 1;
  }
  nextPage(){
    this.currentPage = this.currentPage + 1;
  }
  ngOnInit(): void {
    this.onListAuhthor();
  }
  onListAuhthor(){
    this.authorService.getAll(this.currentPage, this.size).subscribe((res : any) => {
      this.authorList = res.data.authorResponses;
    })
  }
}
