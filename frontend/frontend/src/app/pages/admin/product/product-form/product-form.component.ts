import { Component } from '@angular/core';
import { Book } from '../../../../models/book';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-product-form',
  imports: [FormsModule, NgIf],
  templateUrl: './product-form.component.html',
  styleUrl: './product-form.component.css'
})
export class ProductFormComponent {
  product : any = {
    "title" : "test",
        "format" : "abc",
        "pages" : 12,
        "width" : 12,
        "height" : 23,
        "length" : 43,
        "weight" : 23,
        "publicationDate" : "2023-03-22",
        "publisher" : "ABC",
        "language" : "afnjaf",
        "promotion" : 12,
        "promotionEndDate" : "2023-03-22",
        "description" : "vdgr",
        "sellerReview" : "sgesg",
        "reviewVideo" : "gerger",
        "price" : 2434,
        "stock" : 2,
        "imageUrl" : "gdgthdt",
        "rating" : 3,
        "selling" : 45,
        "createdBy" : "abc",
        "authorId" : 2,
        "nameUser" : "admin",
        "categoryId" : [1 , 2]
  }



  isEditMode: boolean = false;

  selectedFile: File | null = null;

  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) {}

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
        // Hiển thị ảnh ngay lập tức trước khi upload
        const reader = new FileReader();
        reader.onload = (e: any) => {
            this.product.imageUrl = e.target.result; // Hiển thị ảnh trước khi upload
        };
        reader.readAsDataURL(file);

        // Chuẩn bị file để upload lên server
        this.selectedFile = file;
    }
}


  saveProduct() {
    if (this.isEditMode) {
      this.http.put(`http://localhost:8080/api/bookstore/book/update/${this.product.id}`, this.product).subscribe(() => {
        alert('Cập nhật sản phẩm thành công!');
        this.router.navigateByUrl('/admin/products');
      });
    } else {
      this.http.post('http://localhost:8080/api/bookstore/book/create', this.product).subscribe(() => {
        alert('Tạo sản phẩm mới thành công!');
        this.router.navigateByUrl('/admin/products');
      });
    }
  }

  cancel() {
    this.router.navigateByUrl('/admin/products');
  }
}
