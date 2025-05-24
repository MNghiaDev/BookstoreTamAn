import { Component } from '@angular/core';
import { FooterComponent } from "../../layout/footer/footer.component";
import { HeaderComponent } from "../../layout/header/header.component";
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule, NgFor } from '@angular/common';

@Component({
  selector: 'app-order-detail',
  imports: [FooterComponent, HeaderComponent, NgFor, CommonModule],
  templateUrl: './order-detail.component.html',
  styleUrl: './order-detail.component.css'
})
export class OrderDetailComponent {
  order: any;

  constructor(private route: ActivatedRoute, private http: HttpClient) {}

  ngOnInit(): void {
    const orderId = this.route.snapshot.paramMap.get('id');
    this.http.get<any>(`http://localhost:8080/api/orders/my-orders/${orderId}`).subscribe({
      next: (res) => this.order = res.data,
      error: (err) => console.error(err)
    });
  }
}
