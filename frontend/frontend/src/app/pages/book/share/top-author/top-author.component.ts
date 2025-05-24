import { Component, OnInit } from '@angular/core';
import { AuthorService } from '../../../../core/author.service';
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-top-author',
  imports: [NgIf, NgFor, CommonModule, RouterLink],
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
