import { Component, OnInit } from '@angular/core';
import { AuthorService } from '../../services/author.service';
import { CommonModule, NgFor, NgIf } from '@angular/common';

@Component({
  selector: 'app-top-author',
  imports: [NgIf, NgFor, CommonModule],
  templateUrl: './top-author.component.html',
  styleUrl: './top-author.component.css'
})
export class TopAuthorComponent implements OnInit{

  listTopAuthor : any[] = [];

  constructor(private authorService : AuthorService){}

  ngOnInit(): void {
    this.topAuthor();
  }
  topAuthor(){
    this.authorService.topAuthor().subscribe((res : any) => {
      this.listTopAuthor = res.data;
    })
  }
}
