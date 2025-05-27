import { Component, OnInit } from '@angular/core';
import { Author } from '../../../../models/author';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastService } from '../../../../core/toast.service';
import { HttpClient } from '@angular/common/http';
import { AuthorService } from '../../../../core/author.service';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { environment } from '../../../../../environments/environment';

@Component({
  selector: 'app-author-form',
  imports: [FormsModule, NgIf],
  templateUrl: './author-form.component.html',
  styleUrl: './author-form.component.css'
})
export class AuthorFormComponent implements OnInit{
  author : Author = new Author();
  
  isEditMode : boolean = false;
  selectedFile : File | null = null;

  private apiUrl = environment.apiUrl;
  constructor(private router : Router, private route : ActivatedRoute,
    private toastService : ToastService,
    private http : HttpClient,
    private authorService : AuthorService
   ){}

   ngOnInit(): void {
      const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.authorService.getAuthorById(id).subscribe((res:any) => {
        this.author = res.data;
      });
    }
   }
   onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const formData = new FormData();
      formData.append('file', file);
      // 'http://localhost:8080/api/bookstore/upload/image'
      this.http.post<{ fileUrl: string }>( `${this.apiUrl}/upload/image`, formData).subscribe({
      next: (res) => {
        this.author.imageUrl = res.fileUrl;
      },
      error: (err) => {
        console.error("Lỗi upload ảnh", err);
        alert('Lỗi upload ảnh');
      }
    });
    }
  }
  saveAuthor(){
    if(this.isEditMode){
      this.authorService.dupdateAuthor(this.author.id, this.author).subscribe((res : any) =>{
        this.toastService.showToast("Cập nhật tác giả thành công!");
        this.router.navigateByUrl('/admin/authors');
      })
    }else{
      this.authorService.addAuthor(this.author).subscribe((res : any) =>{
      this.toastService.showToast("Thêm mới tác giả thành công!");
      this.router.navigateByUrl("/admin/authors");
      })
    }
  }
  cancel() {
    this.router.navigateByUrl('/admin/authors');
  }
}
