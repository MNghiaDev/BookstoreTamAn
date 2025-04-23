import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { User } from '../../models/user';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-register',
  imports: [FormsModule, NgIf],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  userObj : User = new User();

  retypePassword : string = "";

  constructor(private userService : UserService, private router : Router){

  }

  onRegister(){
    this.userService.register(this.userObj).subscribe((res : any) => {
      alert("Đăng ký thành công!!!");
      this.router.navigateByUrl("login");
    })
  }
}
