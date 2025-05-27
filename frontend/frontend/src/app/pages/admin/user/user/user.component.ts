import { NgClass, NgFor, NgIf } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../../core/user.service';
import { Router } from '@angular/router';
import { environment } from '../../../../../environments/environment';

@Component({
  selector: 'app-user',
  imports: [NgFor, NgIf, NgClass],
  templateUrl: './user.component.html',
  styleUrl: './user.component.css'
})
export class UserComponent implements OnInit {
  users: any[] = [];
  page: number = 0;
  size: number = 10;
  totalPages: number = 0;
  selectedUserId: number | null = null;

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient, private userService : UserService, private router: Router) {}

  ngOnInit() {
    this.fetchUsers();
  }

  fetchUsers() {
    this.http.get<any>(`${this.apiUrl}/user/list?page=${this.page}&size=${this.size}`).subscribe(res => {
      this.users = res.data.user_responses;
      this.totalPages = res.data.total_pages;
    });
  }

  // deleteUser(id: number) {
  //   if (confirm('Bạn có chắc chắn muốn xóa người dùng này?')) {
  //     this.http.delete(`${this.apiUrl}/user/${id}`).subscribe(() => {
  //       this.fetchUsers();
  //     });
  //   }
  // }
selectUser(id: number) {
  this.selectedUserId = id;
}

deleteUser() {
  if (this.selectedUserId === null) return;

  const confirmDelete = confirm('Bạn có chắc chắn muốn xóa người dùng này?');
  if (!confirmDelete) return;

  this.http.delete(`${this.apiUrl}/user/${this.selectedUserId}`).subscribe({
    next: () => {
      alert('Xóa người dùng thành công!');
      this.selectedUserId = null;
      this.fetchUsers();
    },
    error: err => {
      alert('Lỗi khi xóa: ' + (err.error.message || 'Không thể xóa người dùng.'));
    }
  });
}
  goToAddUser(){
    this.router.navigateByUrl('/admin/users/add');
  }
    toggleActive(product: any) {
    const newStatus = !product.active;

    this.http.put(`${this.apiUrl}/user/active/${product.id}`, {
      active: newStatus
    }).subscribe({
      next: (res) => {
        product.active = newStatus;
      },
      error: (err) => {
        alert("Cập nhật trạng thái thất bại!");
        console.error(err);
      }
    });
  }
}
