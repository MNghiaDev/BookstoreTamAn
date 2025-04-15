import { Component, inject, OnInit } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { IListCategory } from '../../models/category';
import { HttpClient } from '@angular/common/http';
import { CategoryService } from '../../services/category/category.service';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-header',
  imports: [RouterLink, RouterOutlet, RouterLinkActive],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit{

  categoryList : IListCategory[] = [];

  categoryChunks: any[] = [];
  constructor(private categoryService : CategoryService){
    this.categoryChunks = this.chunkArray(this.categoryList, 6);
  }

  currentItem : string = "";

  ngOnInit(): void {
    this.getListCategory();
  }

  getListCategory(){
    this.categoryService.listBook().subscribe((res : any) => {
      this.categoryList = res.data;
    })
  }
  chunkArray(arr: any[], size: number) {
    let result = [];
    for (let i = 0; i < arr.length; i += size) {
      result.push(arr.slice(i, i + size));
    }
    return result;
  }
}
