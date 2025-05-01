import { Component, inject, OnInit } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { IListCategory } from '../../models/category';
import { HttpClient } from '@angular/common/http';
import { CategoryService } from '../../services/category.service';
import { NgFor, NgIf } from '@angular/common';
import { TokenService } from '../../services/token.service';
import { jwtDecode } from 'jwt-decode';
import { CartService } from '../../services/cart.service';
import { FooterComponent } from "../footer/footer.component";

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

  cartItemCount: number = 0;

  constructor(private categoryService : CategoryService, private tokenService : TokenService,
    private cartService : CartService
  ){
    this.categoryChunks = this.chunkArray(this.categoryList, 6);
    this.token = this.tokenService.getToken();
    if (this.token) {
      const decoded: any = jwtDecode(this.token);
      this.username = decoded.sub; 
    }
  }

  currentItem : string = "";

  ngOnInit(): void {
    this.getListCategory();
    this.updateCartCount();
  }


toggleDropdown() {
  this.dropdownOpen = !this.dropdownOpen;
}

logout() {
  this.tokenService.removeToken(); 
  this.token = '';             
  this.dropdownOpen = false;
  location.reload();     
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
  updateCartCount() {
    const cart = this.cartService.getLocalCart();
    this.cartItemCount = cart.reduce((sum, item) => sum + item.quantity, 0);
  }
}
