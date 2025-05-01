import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TokenService } from '../../services/token.service';
import { UserService } from '../../services/user.service';
import { error } from 'jquery';
import { jwtDecode } from 'jwt-decode';
import { Router, RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginObj : any = {
    username : "",
    password : ""
  }
  constructor(private userService : UserService, private tokenService : TokenService, private router : Router){}

  onLogin(){
    this.userService.login(this.loginObj).subscribe({
      next: (res: any) => {
        const token = res.data.token;
        this.tokenService.setToken(token);
        const tokenDecoded: any = jwtDecode(token);
        const scope = tokenDecoded.scope;
    
        if (scope === "ROLE_customer") {
          this.router.navigateByUrl("home");
        } else {
          this.router.navigateByUrl("admin");
        }
      },
      error: (err: any) => {
        alert(`Cannot login, error: ${err.error.message}`);
      }
    });
    
  }
}
