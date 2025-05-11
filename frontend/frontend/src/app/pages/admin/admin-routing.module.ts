import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutComponent } from './layout/layout.component';
import { AuthGuard } from '../../services/auth.guard';

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
      { path: 'statistics', loadChildren: () => import('./statistics/statistics/statistics.module').then(m => m.StatisticsModule) }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {}
