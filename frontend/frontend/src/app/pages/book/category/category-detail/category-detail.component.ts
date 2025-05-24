import { Component } from '@angular/core';
import { BookService } from '../../../../core/book.service';
import { ToastService } from '../../../../core/toast.service';
import { CartService } from '../../../../core/cart.service';
import { TokenService } from '../../../../core/token.service';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { AuthorService } from '../../../../core/author.service';
import { CategoryService } from '../../../../core/category.service';
import { Book, IBookList } from '../../../../models/book';
import { CommonModule } from '@angular/common';
import { CategoryComponent } from '../../share/category/category.component';
import { TopAuthorComponent } from '../../share/top-author/top-author.component';
import { NewsComponent } from '../../share/news/news.component';
import { FooterComponent } from '../../../../layout/footer/footer.component';
import { HeaderComponent } from '../../../../layout/header/header.component';
import { FormsModule } from '@angular/forms';
import { Category } from '../../../../models/category';

@Component({
  selector: 'app-category-detail',
  imports: [CommonModule, RouterLink,
    FormsModule, HeaderComponent, FooterComponent],
  templateUrl: './category-detail.component.html',
  styleUrl: './category-detail.component.css'
})
export class CategoryDetailComponent {
  categoryId!: number;
  category: Category = new Category();
  bookList: IBookList[] = [];

  page: number = 0;
  totalPages: number = 0;
  size: number = 8;

  constructor(
    private route: ActivatedRoute,
    private categoryService: CategoryService,
    private bookService: BookService,
    private cartService : CartService,
    private toastService : ToastService
  ) {}

  ngOnInit(): void {
    this.categoryId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadCategory();
    this.loadBooksByCategory();
  }

  loadCategory() {
    this.categoryService.getCategoryById(this.categoryId).subscribe((res: any) => {
      this.category = res.data;
      console.log('Category detail:', res.data);
    });
  }

  loadBooksByCategory() {
    this.bookService.getBooksByCategory(this.categoryId, this.page, this.size)
      .subscribe((res: any) => {
        this.bookList = res.data.productResponses;
        this.totalPages = res.data.totalPages;
      });
  }

  nextPage() {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.loadBooksByCategory();
    }
  }

  previousPage() {
    if (this.page > 0) {
      this.page--;
      this.loadBooksByCategory();
    }
  }
  trackByIndex(index: number, item: any): number {
  return index;
}
 addToCart(item: Book) {
    let cart = this.cartService.getLocalCart();
    const existingItem = cart.find((i: any) => i.bookId === item.id);
  
    if (existingItem) {
      this.toastService.showToast('Sản phẩm đã có trong giỏ hàng!');
      return;
    }
  
    const cartItem = {
      bookId: item.id,
      title: item.title,
      imageUrl: item.imageUrl,
      price: item.price,
      quantity: 1,
      stock: item.stock
    };
  
    cart.push(cartItem);
    this.cartService.saveLocalCart(cart);
    this.toastService.showToast('Thêm sản phẩm vào giỏ hàng thành công!');
  }
}
