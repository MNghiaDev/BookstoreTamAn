import { Component, OnInit } from '@angular/core';
import { FooterComponent } from "../../layout/footer/footer.component";
import { HeaderComponent } from "../../layout/header/header.component";
import { CommonModule, NgClass, NgFor, NgIf } from '@angular/common';
import { IListOrder, Order } from '../../models/order';
import { OrderService } from '../../core/order.service';
import { ToastService } from '../../core/toast.service';

@Component({
  selector: 'app-my-order',
  imports: [FooterComponent, HeaderComponent, NgFor, CommonModule, NgIf],
  templateUrl: './my-order.component.html',
  styleUrl: './my-order.component.css'
})
export class MyOrderComponent  implements OnInit{
  orderList : IListOrder[] = [];

  page : number = 0;
  size : number = 3;
  totalPages : number = 0;

  selectedOrder: any = null;

  orderDetailList : any[] = [];
  
  nextPage() {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.listOrder();
    }
  }

  previousPage() {
    if (this.page > 0) {
      this.page--;
      this.listOrder();
    }
  }

  viewOrderDetail(order: IListOrder) {
    debugger
    this.selectedOrder = order;


  }

  constructor(private orderService : OrderService, private toastService : ToastService){

  }

  ngOnInit(): void {
    this.listOrder();
  }

  listOrder(){
    this.orderService.myOrder(this.page, this.size).subscribe((res: any) => {
      this.orderList = res.data.orderResponses;
      this.totalPages = res.data.totalPages;
    })
  }

  listOrderDetail(){
    
  }
  confirmReceived(orderId: number){
    if (!orderId) {
      this.toastService.showToast("Đơn hàng không hợp lệ!");
      return;
    }
    this.orderService.confirmOrderReceived(orderId).subscribe(() => {
      this.toastService.showToast('Bạn đã xác nhận đã nhận hàng. Cảm ơn bạn!')
      this.listOrder();
    });
  }
  selectOrder(order: Order) {
    this.selectedOrder = order;
    this.orderService.orderDetail(order.orderId).subscribe((res : any) => {
      debugger
        this.orderDetailList = res.data;
    })
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
  cancelOrder(orderId : number){
    if (!orderId) {
      this.toastService.showToast("Đơn hàng không hợp lệ!");
      return;
    }
    this.orderService.cancelOrderReceived(orderId).subscribe(() => {
      this.toastService.showToast('Bạn đã hủy đơn hàng thành công!')
      this.listOrder();
    });
  }
}
