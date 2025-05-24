import { NgClass, NgFor } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-product-list',
  imports: [NgClass],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent implements OnInit {
products: any[] = [];
  selectedProductId: number | null = null;
  page: number = 0;
  size: number = 10;
  totalPages: number = 0;
  stt : number = 0;
  


  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit() {
    this.fetchProducts();
  }

  fetchProducts() {
    this.http.get<any>(`http://localhost:8080/api/bookstore/book?page=${this.page}&size=${this.size}`)
      .subscribe(res => {
        this.products = res.data.productResponses;
        this.totalPages = res.data.totalPages;
      });
  }

  deleteProduct() {
    if (this.selectedProductId !== null && confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')) {
      this.http.delete(`http://localhost:8080/api/bookstore/book/delete/${this.selectedProductId}`).subscribe(() => {
        alert('Xóa sản phẩm thành công!');
        this.selectedProductId = null;
        this.fetchProducts();
      });
    }
  }

  selectProduct(id: number) {
    this.selectedProductId = id;
  }

  goToAddProduct() {
    this.router.navigateByUrl('/admin/products/add');
  }

  goToEditProduct() {
    if (this.selectedProductId !== null) {
      this.router.navigateByUrl('/admin/products/edit/' + this.selectedProductId);
    }
  }

  nextPage() {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.fetchProducts();
    }
  }

  previousPage() {
    if (this.page > 0) {
      this.page--;
      this.fetchProducts();
    }
  }

  toggleActive(product: any) {
    const newStatus = !product.active;

    this.http.put(`http://localhost:8080/api/bookstore/book/active/${product.id}`, {
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
