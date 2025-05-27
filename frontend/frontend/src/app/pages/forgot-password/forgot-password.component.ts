import { NgIf } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-forgot-password',
  imports: [FormsModule, NgIf, RouterLink],
  templateUrl: './forgot-password.component.html',
  styleUrl: './forgot-password.component.css'
})
export class ForgotPasswordComponent {
  step = 1;
  email = '';
  otp = '';
  newPassword = '';
  retypePassword : string = "";

  private apiUrl = environment.apiUrl;

  constructor(private router : Router, private http : HttpClient){}
sendOTP() {
  this.http.post(`${this.apiUrl}/auth/forgot-password`, { email: this.email }).subscribe({
    next: (res) => {
      console.log("Gửi OTP thành công:", res); // ✅ Thêm dòng này để kiểm tra phản hồi
      alert('OTP đã được gửi qua email');
      this.step = 2;
    },
    error: (err) => {
      console.error("Lỗi gửi OTP:", err); // ✅ Thêm dòng này để kiểm tra lỗi thật
      alert('Không thể gửi OTP');
    }
  });
}

  resetPassword() {
    const payload = {
      email: this.email,
      otp: this.otp,
      newPassword: this.newPassword
    };

    this.http.post(`${this.apiUrl}/auth/reset-password`, payload).subscribe({
      next: () => {
        alert('Đặt lại mật khẩu thành công');
        this.router.navigate(['/login']);
      },
      error: () => alert('OTP không đúng hoặc hết hạn')
    });
  }
}
