import { Component, inject, OnInit } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { IListCategory } from '../../models/category';
import { HttpClient } from '@angular/common/http';
import { CategoryService } from '../../services/category.service';
import { NgFor, NgIf } from '@angular/common';
import { TokenService } from '../../services/token.service';
import { jwtDecode } from 'jwt-decode';

@Component({
  selector: 'app-header',
  imports: [RouterLink, RouterOutlet, RouterLinkActive, NgIf],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit{

  categoryList : IListCategory[] = [];
  username: string = '';
  token: any = "";
  dropdownOpen = false;
  categoryChunks: any[] = [];
  constructor(private categoryService : CategoryService, private tokenService : TokenService){
    this.categoryChunks = this.chunkArray(this.categoryList, 6);
    this.token = this.tokenService.getToken();
    if (this.token) {
      const decoded: any = jwtDecode(this.token);
      this.username = decoded.sub; // hoặc decoded.username nếu bạn dùng key khác
    }
  }

  currentItem : string = "";

  ngOnInit(): void {
    this.getListCategory();
  }


toggleDropdown() {
  this.dropdownOpen = !this.dropdownOpen;
}

logout() {
  this.tokenService.removeToken(); // Xóa token
  this.token = '';                // Xóa trong component
  this.dropdownOpen = false;
  location.reload();              // Hoặc dùng this.router.navigateByUrl('/login');
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
