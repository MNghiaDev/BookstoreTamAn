import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { CheckoutComponent } from './pages/cart/checkout/checkout.component';
import { ForgotPasswordComponent } from './pages/forgot-password/forgot-password.component';
import { RegisterComponent } from './pages/register/register.component';
import { UpdateInfoAccountComponent } from './pages/update-info-account/update-info-account.component';
import { PaymentCallbackComponent } from './pages/payment-callback/payment-callback.component';
import { ThankYouComponent } from './pages/thank-you/thank-you.component';
import { MyOrderComponent } from './pages/my-order/my-order.component';

export const routes: Routes = [
  { path: '', loadChildren: () => import('./pages/home/home.module').then(m => m.HomeModule) },
  { path: 'books', loadChildren: () => import('./pages/book/book.module').then(m => m.BookModule) },
  { path: 'cart', loadChildren: () => import('./pages/cart/cart.module').then(m => m.CartModule) },
  { path: 'admin', loadChildren: () => import('./pages/admin/admin.module').then(m => m.AdminModule) },
  { path: 'login', component : LoginComponent},
  { path: 'checkout', component : CheckoutComponent},
  { path: 'forgot-password', component : ForgotPasswordComponent},
  { path: 'register', component : RegisterComponent},
  { path: 'update-info-account', component : UpdateInfoAccountComponent},
  { path: 'payment-callback', component: PaymentCallbackComponent},
  { path: 'thank-you', component: ThankYouComponent},
  { path: 'my-order', component: MyOrderComponent}
];
