import { NgFor } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../../services/user.service';

@Component({
  selector: 'app-user',
  imports: [NgFor],
  templateUrl: './user.component.html',
  styleUrl: './user.component.css'
})
export class UserComponent implements OnInit {
  users: any[] = [];
  page: number = 0;
  size: number = 10;
  totalPages: number = 0;

  constructor(private http: HttpClient, private userService : UserService) {}

  ngOnInit() {
    this.fetchUsers();
  }

  fetchUsers() {
    this.http.get<any>(`http://localhost:8080/api/bookstore/user/list?page=${this.page}&size=${this.size}`).subscribe(res => {
      this.users = res.data.user_responses;
      this.totalPages = res.data.total_pages;
    });
  }

  deleteUser(id: number) {
    if (confirm('Bạn có chắc chắn muốn xóa người dùng này?')) {
      this.http.delete(`http://localhost:8080/api/bookstore/user/${id}`).subscribe(() => {
        this.fetchUsers();
      });
    }
  }
}
