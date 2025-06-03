import { Component } from '@angular/core';
import { INewsList } from '../../../../models/news';
import { environment } from '../../../../../environments/environment';
import { NewsService } from '../../../../core/news.service';
import { Router } from '@angular/router';
import { ToastService } from '../../../../core/toast.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-news-list',
  imports: [],
  templateUrl: './news-list.component.html',
  styleUrl: './news-list.component.css'
})
export class NewsListComponent {
  news : INewsList[] = [];
  selectnewsId : number | null = null;
  page : number = 0;
  size : number = 5;
  totalPages : number = 0;
  stt : number = 0;

  private apiUrl = environment.apiUrl;
  constructor(private newsService : NewsService, 
    private router : Router,
    private toastService : ToastService,
    private http : HttpClient
  ){}

  ngOnInit(): void {
    this.fetchnews();
  }

  fetchnews(){
    this.newsService.getList(this.page, this.size).subscribe((res : any) =>{
      this.news = res.data.newsResponses;
      this.totalPages = res.data.totalPages;
    })
  }

  deletenews(){
    if(this.selectnewsId !== null && confirm('Bạn có chắc chắn muốn xóa tin này?')){
      this.newsService.delete(this.selectnewsId).subscribe((res :any) =>{
        this.toastService.showToast("Xóa tin thành công!");
      })
    }
  }
  selectnews(id : number){
    this.selectnewsId = id;
  }
  goToAddnews(){
    this.router.navigateByUrl('/admin/news/add');
  }
  goToEditnews(){
    if(this.selectnewsId !== null){
      this.router.navigateByUrl('/admin/news/edit/' + this.selectnewsId);
    }
  }
    nextPage() {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.fetchnews();
    }
  }

  previousPage() {
    if (this.page > 0) {
      this.page--;
      this.fetchnews();
    }
  }
}
