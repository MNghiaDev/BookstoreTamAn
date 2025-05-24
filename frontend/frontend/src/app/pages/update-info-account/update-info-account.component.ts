import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TokenService } from '../../core/token.service';
import { HttpClient } from '@angular/common/http';
import { ToastService } from '../../core/toast.service';
import { HeaderComponent } from "../../layout/header/header.component";
import { FooterComponent } from "../../layout/footer/footer.component";
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-update-info-account',
  imports: [ReactiveFormsModule, HeaderComponent, FooterComponent, NgIf],
  templateUrl: './update-info-account.component.html',
  styleUrl: './update-info-account.component.css'
})
export class UpdateInfoAccountComponent {
  infoForm!: FormGroup;
  passwordForm!: FormGroup;
  userId!: number;
  
  
  constructor(
    private fb: FormBuilder,
    private tokenService: TokenService,
    private http: HttpClient, private toastService : ToastService
  ) {}

  ngOnInit(): void {
    const token: any = this.tokenService.getToken();
    const decoded: any = token ? JSON.parse(atob(token.split('.')[1])) : null;
    this.userId = decoded?.user;

    this.loadUserInfo();

    this.infoForm = this.fb.group({
      full_name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: [''],
      address: ['']
    });

      this.passwordForm = this.fb.group({
      oldPassword: ['', Validators.required],
      newPassword: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    }, {
      validators: this.passwordsMatchValidator
    });
  }
  passwordsMatchValidator(form: FormGroup) {
    const newPassword = form.get('newPassword')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;

    return newPassword === confirmPassword ? null : { passwordsMismatch: true };
  }

  loadUserInfo() {
    this.http.get<any>(`http://localhost:8080/api/bookstore/user/info/${this.userId}`).subscribe(data => {
      this.infoForm.patchValue(data.data);
    });
  }

  onUpdateInfo() {
    this.http.post(`http://localhost:8080/api/bookstore/user/update/${this.userId}`, this.infoForm.value)
      .subscribe(() => {
        this.toastService.showToast("Cập nhật thông tin thành công!");
      });
  }

  onChangePassword() {
    this.http.post(`http://localhost:8080/api/bookstore/user/change-password/${this.userId}`, this.passwordForm.value)
      .subscribe({
        next: () => this.toastService.showToast("Đổi mật khẩu thành công!"),
        error: err => alert("Mật khẩu cũ không đúng hoặc có lỗi!")
      });
  }
  
}
