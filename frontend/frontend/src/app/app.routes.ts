import { Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { BookComponent } from './pages/book/book.component';
import { BookDetailComponent } from './pages/book-detail/book-detail.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
    },
    {
        path: 'home',
        component: HomeComponent
    },
    {
        path: 'book',
        component: BookComponent
    },
    {
        path: 'book-detail/:id',
        component: BookDetailComponent
    },
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: 'register',
        component: RegisterComponent
    }
];
