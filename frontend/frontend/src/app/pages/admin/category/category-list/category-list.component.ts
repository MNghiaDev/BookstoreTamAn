import { Component } from '@angular/core';
import { Category, IListCategory } from '../../../../models/category';
import { CategoryService } from '../../../../core/category.service';
import { Router } from '@angular/router';
import { ToastService } from '../../../../core/toast.service';
import { HttpClient } from '@angular/common/http';
import { NgClass } from '@angular/common';
import { environment } from '../../../../../environments/environment';

@Component({
  selector: 'app-category-list',
  imports: [ NgClass],
  templateUrl: './category-list.component.html',
  styleUrl: './category-list.component.css'
})
export class CategoryListComponent {
  categories : IListCategory[] = [];
  selectCategoryId : number | null = null;
  page : number = 0;
  size : number = 5;
  totalPages : number = 0;
  stt : number = 0;
  private apiUrl = environment.apiUrl;

  constructor(private categoryService : CategoryService, 
    private router : Router,
    private toastService : ToastService,
    private http : HttpClient
  ){}

  ngOnInit(): void {
    this.fetchCategories();
  }

  fetchCategories(){
    this.categoryService.listCategory(this.page, this.size).subscribe((res : any) =>{
      this.categories = res.data.categoryResponses;
      this.totalPages = res.data.totalPages;
    })
  }
  deleteCategory(){
    if(this.selectCategoryId !== null && confirm('Bạn có chắc chắn muốn xóa thể loại này?')){
      this.categoryService.deleteCategory(this.selectCategoryId).subscribe((res :any) =>{
        this.toastService.showToast("Xóa thể loại thành công!");
      })
    }
  }
  selectCategory(id : number){
    this.selectCategoryId = id;
  }
  goToAddCategory(){
    this.router.navigateByUrl('/admin/categories/add');
  }
  goToEditCategory(){
    if(this.selectCategoryId !== null){
      this.router.navigateByUrl('/admin/categories/edit/' + this.selectCategoryId);
    }
  }
    nextPage() {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.fetchCategories();
    }
  }

  previousPage() {
    if (this.page > 0) {
      this.page--;
      this.fetchCategories();
    }
  }
    toggleActive(category: any) {
    const newStatus = !category.active;

    this.http.put(`${this.apiUrl}/category/active/${category.id}`, {
      active: newStatus
    }).subscribe({
      next: (res) => {
        category.active = newStatus;
      },
      error: (err) => {
        alert("Cập nhật trạng thái thất bại!");
        console.error(err);
      }
    });
  }
}
