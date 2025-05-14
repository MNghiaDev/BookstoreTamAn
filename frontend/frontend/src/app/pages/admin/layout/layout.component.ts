import { Component } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { TokenService } from '../../../services/token.service';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css'],
})
export class LayoutComponent {

  constructor(private router: Router, private tokenService : TokenService) {}

  logout(): void {
    this.tokenService.removeToken();
    this.router.navigate(['/login']);
  }
}
