import { Component } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { ToastComponent } from "./shared/toast/toast.component";
import { HeaderComponent } from "./layout/header/header.component";
import { FooterComponent } from "./layout/footer/footer.component";
import { NgIf } from '@angular/common';
import { TokenService } from './core/token.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ToastComponent, HeaderComponent, FooterComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'TamAn';
  showHeaderFooter = true;

  constructor(private router: Router, private tokenService: TokenService) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        const hiddenRoutes = ['/login', '/register'];
        this.showHeaderFooter = !hiddenRoutes.includes(event.urlAfterRedirects);
      }
    });
  }
}
