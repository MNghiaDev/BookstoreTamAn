import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { User } from '../../../../models/user';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { NgIf } from '@angular/common';
import { environment } from '../../../../../environments/environment';

@Component({
  selector: 'app-user-form',
  imports: [FormsModule, NgIf],
  templateUrl: './user-form.component.html',
  styleUrl: './user-form.component.css'
})
export class UserFormComponent {
    user = {
    username: '',
    password: '',
    email: '',
    full_name: '',
    phone: '',
    address: '',
    role: ''
  };

  confirmPassword: string = '';
  
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient, private router: Router) {}

confirmPasswordMismatch = false;

saveUser() {
  this.confirmPasswordMismatch = this.user.password !== this.confirmPassword;
  if (this.confirmPasswordMismatch) return;

  this.http.post(`${this.apiUrl}/user/register`, this.user).subscribe({
    next: () => {
      alert('Tạo người dùng thành công!');
      this.router.navigate(['/admin/users']);
    },
    error: err => {
      alert('Lỗi: ' + (err.error.message || 'Không thể tạo người dùng!'));
    }
  });
}


  cancel() {
    this.router.navigate(['/admin/users']);
  }
}
