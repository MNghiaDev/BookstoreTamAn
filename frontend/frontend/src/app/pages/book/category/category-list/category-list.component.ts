import { Component } from '@angular/core';
import { IListCategory } from '../../../../models/category';
import { CategoryService } from '../../../../core/category.service';

@Component({
  selector: 'app-category-list',
  imports: [],
  templateUrl: './category-list.component.html',
  styleUrl: './category-list.component.css'
})
export class CategoryListComponent {
  categoryList : IListCategory[] = [];

  currentPage : number = 0;
  size : number = 15;


  constructor(private categoryService : CategoryService){

  }
  onLoadCategory(){
    this.categoryService.listCategory(this.currentPage, this.size).subscribe((res:any) =>{
      
    })
  }
}
