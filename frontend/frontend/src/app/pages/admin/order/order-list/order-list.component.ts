import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormsModule, NgModel, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-order-list',
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './order-list.component.html',
  styleUrl: './order-list.component.css'
})
export class OrderListComponent {
  orders: any[] = [];
  selectedOrderId: number | null = null;
  page = 0;
  size = 10;
  totalPages = 0;
  statusForm: FormGroup;
  filterStatus: string = '';

  constructor(private http: HttpClient, private fb: FormBuilder) {
    this.statusForm = this.fb.group({
      status: ['']
    });
  }

  ngOnInit(): void {
    this.fetchOrders();
  }

  fetchOrders() {
    let apiUrl = `http://localhost:8080/api/bookstore/order/list?page=${this.page}&size=${this.size}`;

    if (this.filterStatus) {
      apiUrl += `&status=${this.filterStatus}`;
    }

    this.http.get<any>(apiUrl).subscribe(res => {
      this.orders = res.data.orderResponses;
      this.totalPages = res.data.totalPages;
    });
  }

  selectOrder(id: number) {
    this.selectedOrderId = id;
  }

  deleteOrder() {
    if (this.selectedOrderId == null) return;
    if (confirm('Bạn có chắc chắn muốn xóa đơn hàng này?')) {
      this.http.delete(`http://localhost:8080/api/bookstore/order/delete/${this.selectedOrderId}`)
        .subscribe(() => {
          alert('Đã xóa đơn hàng');
          this.selectedOrderId = null;
          this.fetchOrders();
        });
    }
  }

  updateStatus() {
    if (this.selectedOrderId == null) return;
    const status = this.statusForm.get('status')?.value;
    if (!status) return alert('Vui lòng chọn trạng thái');

    this.http.put(`http://localhost:8080/api/bookstore/order/update-status/${this.selectedOrderId}`, { status })
      .subscribe(() => {
        alert('Cập nhật trạng thái thành công');
        this.fetchOrders();
      });
  }
  get statusControl(): FormControl {
    return this.statusForm.get('status') as FormControl;
  }
  getStatusLabel(status: string): string {
    switch (status) {
      case 'pending': return 'Chờ xử lý';
      case 'processing': return 'Đang xử lý';
      case 'shipped': return 'Đang giao';
      case 'delivered': return 'Đã giao';
      case 'canceled': return 'Đã hủy';
      default: return status;
    }
  }
  previousPage() {
  if (this.page > 0) {
    this.page--;
    this.fetchOrders();
  }
  }

  nextPage() {
    if (this.page + 1 < this.totalPages) {
      this.page++;
      this.fetchOrders();
    }
  }
    toggleActive(product: any) {
    const newStatus = !product.active;

    this.http.put(`http://localhost:8080/api/bookstore/order/active/${product.id}`, {
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
