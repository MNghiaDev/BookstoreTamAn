import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TokenService } from '../../core/token.service';
import { UserService } from '../../core/user.service';
import { error } from 'jquery';
import { jwtDecode } from 'jwt-decode';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { NgIf } from '@angular/common';

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

  message : string = "";
  constructor(private userService : UserService, private tokenService : TokenService, private router : Router){}

  onLogin(){
    this.userService.login(this.loginObj).subscribe({
      next: (res: any) => {
        const token = res.data.token;
        this.tokenService.setToken(token);
        const tokenDecoded: any = jwtDecode(token);
        const scopes = tokenDecoded.scope;
    
        if (scopes.includes("ROLE_customer")) {
          this.router.navigateByUrl("");
        } else if (scopes.includes("ROLE_admin")) {
          this.router.navigateByUrl("/admin");
        } else {
          alert("Không xác định được quyền truy cập.");
        }
      },
      error: (err: any) => {
        // alert(`Cannot login, error: ${err.error.message}`);
        this.message = err.error.message;
      }
    });
    
  }
}
