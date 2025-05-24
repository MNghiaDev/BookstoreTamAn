import { Component, ElementRef, HostListener, inject, OnInit, ViewChild } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { IListCategory } from '../../models/category';
import { HttpClient } from '@angular/common/http';
import { CategoryService } from '../../core/category.service';
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { TokenService } from '../../core/token.service';
import { jwtDecode } from 'jwt-decode';
import { CartService } from '../../core/cart.service';
import { FooterComponent } from "../footer/footer.component";
import { BookService } from '../../core/book.service';
import { ToastService } from '../../core/toast.service';
import { Book, IBookList } from '../../models/book';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-header',
  imports: [RouterLink, RouterOutlet, NgIf, CommonModule, FormsModule],
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
  searchKeyword: string = '';
  suggestions: string[] = [];

    @ViewChild('userDropdown', { static: false }) userDropdownRef!: ElementRef;


  constructor(private categoryService : CategoryService, private tokenService : TokenService,
    private cartService : CartService, private bookService: BookService, private toastService : ToastService,
    private router : Router, private eRef: ElementRef
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
    this.loadCart();
  }

   @HostListener('document:click', ['$event'])
  onClickOutside(event: MouseEvent) {
    const clickedInside = this.eRef.nativeElement.contains(event.target);
    if (!clickedInside) {
      this.dropdownOpen = false;
    }
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

  totalPrice : number = 0;
  cartItems: any[] = [];

  loadCart() {
    this.cartItems = this.cartService.getLocalCart().map(item => ({
      ...item,
      selected: false
    }));
    this.calculateTotalPrice();
  }

  calculateTotalPrice() {
    this.totalPrice = this.cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);
  }

  
  onSearch(): void {
  if (this.searchKeyword.trim() !== '') {
    this.router.navigate(['/book'], {
      queryParams: { keyword: this.searchKeyword.trim() }
    });
  }
}

  onKeywordChange(): void {
    if (this.searchKeyword.trim() === '') {
      this.suggestions = [];
      return;
    }

    this.bookService.getSuggestions(this.searchKeyword).subscribe((res: any) => {
      this.suggestions = res.data || [];
    });
  }

  selectSuggestion(suggestion: string): void {
    this.searchKeyword = suggestion;
    this.suggestions = [];
    this.onSearch();  // Chuyển ngay khi chọn gợi ý
  }
}
