import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule, NgFor, NgIf } from '@angular/common';

interface SalesStatistics {
  totalRevenue: number;
  totalOrders: number;
  totalProductsSold: number;
}

interface TopSellingProduct {
  bookTitle: string;
  totalSold: number;
}

interface PurchaseTrend {
  [date: string]: number;
}

@Component({
  selector: 'app-statistics',
  imports: [FormsModule, NgFor, NgIf, CommonModule],
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {
  startDate: string = '';
  endDate: string = '';
  salesStatistics: SalesStatistics | null = null;
  topSellingProducts: TopSellingProduct[] = [];
  purchaseTrend: PurchaseTrend = {};

  constructor(private http: HttpClient) {}

  ngOnInit(): void {}

  fetchStatistics(): void {
    if (!this.startDate || !this.endDate) {
      alert("Vui lòng chọn ngày bắt đầu và ngày kết thúc.");
      return;
    }

    // Fetch Summary
    this.http.get<SalesStatistics>(`http://localhost:8080/api/bookstore/statistics/summary`, {
      params: {
        startDate: this.startDate,
        endDate: this.endDate
      }
    }).subscribe(data => this.salesStatistics = data);

    // Fetch Top Selling Products
    this.http.get<TopSellingProduct[]>(`http://localhost:8080/api/bookstore/statistics/top-products`, {
      params: {
        startDate: this.startDate,
        endDate: this.endDate
      }
    }).subscribe(data => this.topSellingProducts = data);

    // Fetch Purchase Trend
    this.http.get<PurchaseTrend>(`http://localhost:8080/api/bookstore/statistics/trend`, {
      params: {
        startDate: this.startDate,
        endDate: this.endDate
      }
    }).subscribe(data => this.purchaseTrend = data);
  }

  exportStatistics(): void {
    window.open(`http://localhost:8080/api/bookstore/statistics/export?startDate=${this.startDate}&endDate=${this.endDate}`);
  }

  getPurchaseTrendKeys(): string[] {
    return Object.keys(this.purchaseTrend);
  }
}
