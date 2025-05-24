import { Component } from '@angular/core';
import { Book } from '../../../../models/book';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { ToastService } from '../../../../core/toast.service';

@Component({
  selector: 'app-product-form',
  imports: [FormsModule, NgIf],
  templateUrl: './product-form.component.html',
  styleUrl: './product-form.component.css'
})
export class ProductFormComponent {
  product : any = {
    "title" : "",
        "format" : "",
        "pages" : 0,
        "width" : 0,
        "height" : 0,
        "length" : 0,
        "weight" : 0,
        "publicationDate" : "",
        "publisher" : "",
        "language" : "",
        "promotion" : 0,
        "promotionEndDate" : "",
        "description" : "",
        "sellerReview" : "",
        "reviewVideo" : "",
        "price" : 0,
        "stock" : 0,
        "imageUrl" : "",
        "rating" : 0,
        "selling" : 0,
        "createdBy" : "",
        "authorName" : 0,
        "nameUser" : "",
        "categoryName" : []
  }



  isEditMode: boolean = false;

  selectedFile: File | null = null;

  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute, private toastService : ToastService) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.http.get<any>(`http://localhost:8080/api/bookstore/book/${id}`).subscribe(res => {
        this.product = res.data;
      });
    }
  }

onFileSelected(event: any) {
  const file = event.target.files[0];
  if (file) {
    const formData = new FormData();
    formData.append('file', file);
    this.http.post<{ fileUrl: string }>('http://localhost:8080/api/bookstore/upload/image', formData).subscribe({
    next: (res) => {
      this.product.imageUrl = res.fileUrl;
    },
    error: (err) => {
      console.error("Lỗi upload ảnh", err);
      alert('Lỗi upload ảnh');
    }
  });
  }
}



  saveProduct() {
     if (typeof this.product.categoryName === 'string') {
    this.product.categoryName = this.product.categoryName.split(',').map((s: string) => s.trim());
    }
    if (this.isEditMode) {
      this.http.put(`http://localhost:8080/api/bookstore/book/update/${this.product.id}`, this.product).subscribe(() => {
        this.toastService.showToast("Cập nhật sản phẩm thành công!")
        this.router.navigateByUrl('/admin/products');
      });
    } else {
      this.http.post('http://localhost:8080/api/bookstore/book/create', this.product).subscribe({
      next: () => {
        this.toastService.showToast("Tạo sản phẩm mới thành công!")
        this.router.navigateByUrl('/admin/products');
      },
      error: (err) => {
        if (err.error && err.error.message) {
          alert(err.error.message);
        } else {
          alert('Có lỗi xảy ra khi tạo sản phẩm');
        }
      }
    });

    }
  }

  cancel() {
    this.router.navigateByUrl('/admin/products');
  }
}
