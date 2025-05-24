import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutComponent } from './layout/layout.component';
import { AuthGuard } from '../../core/auth.guard';

const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      {
        path: 'users',
        loadChildren: () =>
          import('./user/user.module').then((m) => m.UserModule)
      },
      {
        path: 'products',
        loadChildren: () => import('./product/product.module').then(m => m.ProductModule)
      },
      { path: 'statistics', loadChildren: () => import('./statistics/statistics/statistics.module').then(m => m.StatisticsModule) },
      {
        path: 'orders',
        loadChildren: () => import('./order/order.module').then(m => m.OrderModule)
      },
      {
        path: 'authors',
        loadChildren: () => import('./author/author.module').then(m => m.AuthorModule)
      },
      {
        path: 'categories',
        loadChildren: () => import('./category/category.module').then(m => m.CategoryModule)
      },
      {
        path: 'news',
        loadChildren: () => import('./news/news.module').then(m => m.NewsModule)
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {}
