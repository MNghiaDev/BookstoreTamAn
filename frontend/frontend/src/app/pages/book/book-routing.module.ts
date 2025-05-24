import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from '../home/home/home.component';
import { BookComponent } from './book/book.component';
import { BookDetailComponent } from './book-detail/book-detail.component';
import { AuthorListComponent } from './author/author-list/author-list.component';
import { CategoryComponent } from './share/category/category.component';
import { NewsListComponent } from './news/news-list/news-list.component';
import { AuthorDetailComponent } from './author/author-detail/author-detail.component';
import { CategoryDetailComponent } from './category/category-detail/category-detail.component';

const routes: Routes = [
  {path : '' , component :BookComponent},
  {path : 'book-detail/:id', component: BookDetailComponent},
  {path : 'authors', component: AuthorListComponent},
  {path : 'authors-detail/:id', component: AuthorDetailComponent},
  {path : 'category-detail/:id', component: CategoryDetailComponent},
  {
        path: 'authors',
        loadChildren: () =>
        import('./author/author.module').then((m) => m.AuthorModule)
  },
  {
        path: 'categories',
        loadChildren: () =>
        import('./category/category.module').then((m) => m.CategoryModule)
  },
  {
        path: 'news',
        loadChildren: () =>
          import('./news/news.module').then((m) => m.NewsModule)
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookRoutingModule { }
