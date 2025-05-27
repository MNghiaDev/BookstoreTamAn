import { Component } from '@angular/core';
import { FooterComponent } from "../../layout/footer/footer.component";
import { HeaderComponent } from "../../layout/header/header.component";
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule, NgFor } from '@angular/common';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-order-detail',
  imports: [FooterComponent, HeaderComponent, NgFor, CommonModule],
  templateUrl: './order-detail.component.html',
  styleUrl: './order-detail.component.css'
})
export class OrderDetailComponent {
  order: any;

  private apiUrl = environment.apiUrl;

  constructor(private route: ActivatedRoute, private http: HttpClient) {}

  ngOnInit(): void {
    const orderId = this.route.snapshot.paramMap.get('id');
    this.http.get<any>(`${this.apiUrl}/orders/my-orders/${orderId}`).subscribe({
      next: (res) => this.order = res.data,
      error: (err) => console.error(err)
    });
  }
}
