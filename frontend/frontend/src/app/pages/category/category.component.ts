import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../../services/category.service';
import { NgFor, NgIf } from '@angular/common';

@Component({
  selector: 'app-category',
  imports: [NgIf, NgFor],
  templateUrl: './category.component.html',
  styleUrl: './category.component.css'
})
export class CategoryComponent implements OnInit {

  listCategory : any[] = []


  constructor(private categoryService : CategoryService){}

  ngOnInit(): void {
    this.onCountProduct();
  }

  onCountProduct(){
    debugger
    this.categoryService.countProduct().subscribe((res : any) => {
      debugger;
      this.listCategory = res.data;
    })
  }
}
