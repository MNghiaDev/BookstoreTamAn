import { Component, OnInit } from '@angular/core';
import { Author, IAuthorList } from '../../../../models/author';
import { AuthorService } from '../../../../core/author.service';
import { Router } from '@angular/router';
import { ToastComponent } from '../../../../shared/toast/toast.component';
import { ToastService } from '../../../../core/toast.service';
import { NgClass } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-author-list',
  imports: [NgClass],
  templateUrl: './author-list.component.html',
  styleUrl: './author-list.component.css'
})
export class AuthorListComponent implements OnInit{
  authors : IAuthorList[] = [];
  selectAuthorId : number | null = null;
  page : number = 0;
  size : number = 5;
  totalPages : number = 0;
  stt : number = 0;

  constructor(private authorService : AuthorService, 
    private router : Router,
    private toastService : ToastService,
    private http : HttpClient
  ){}

  ngOnInit(): void {
    this.fetchAuthors();
  }

  fetchAuthors(){
    this.authorService.getAll(this.page, this.size).subscribe((res : any) =>{
      this.authors = res.data.authorResponses;
      this.totalPages = res.data.totalPages;
    })
  }
  deleteAuthor(){
    if(this.selectAuthorId !== null && confirm('Bạn có chắc chắn muốn xóa tác giả này?')){
      this.authorService.deleteAuthor(this.selectAuthorId).subscribe((res :any) =>{
        this.toastService.showToast("Xóa tác giả thành công!");
      })
    }
  }
  selectAuthor(id : number){
    this.selectAuthorId = id;
  }
  goToAddAuthor(){
    this.router.navigateByUrl('/admin/authors/add');
  }
  goToEditAuthor(){
    if(this.selectAuthorId !== null){
      this.router.navigateByUrl('/admin/authors/edit/' + this.selectAuthorId);
    }
  }
    nextPage() {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.fetchAuthors();
    }
  }

  previousPage() {
    if (this.page > 0) {
      this.page--;
      this.fetchAuthors();
    }
  }
    toggleActive(product: any) {
    const newStatus = !product.active;

    this.http.put(`http://localhost:8080/api/bookstore/author/active/${product.id}`, {
      active: newStatus
    }).subscribe({
      next: (res) => {
        product.active = newStatus;
      },
      error: (err) => {
        alert("Cập nhật trạng thái thất bại!");
        console.error(err);
      }
    });
  }
}
