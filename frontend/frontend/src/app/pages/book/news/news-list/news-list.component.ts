import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { INewsList } from '../../../../models/news';
import { NewsService } from '../../../../core/news.service';
import { HeaderComponent } from "../../../../layout/header/header.component";
import { CategoryComponent } from "../../share/category/category.component";
import { TopAuthorComponent } from "../../share/top-author/top-author.component";

@Component({
  selector: 'app-news-list',
  imports: [RouterLink, HeaderComponent, CategoryComponent, TopAuthorComponent],
  templateUrl: './news-list.component.html',
  styleUrl: './news-list.component.css'
})
export class NewsListComponent implements OnInit{
  newsList : INewsList [] = [];

  currentPage : number = 0;
  size : number = 15;

  constructor(private newsService : NewsService){}

  ngOnInit(): void {
    this.onLoadNewsList();
  }

  onLoadNewsList(){
    this.newsService.getList(this.currentPage, this.size).subscribe((res : any) => {
      this.newsList = res.data.newsResponses;
    })
  }
}
