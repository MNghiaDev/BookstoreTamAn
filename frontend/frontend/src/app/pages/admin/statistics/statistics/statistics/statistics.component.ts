import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule, NgModel } from '@angular/forms';
import { CommonModule, NgIf } from '@angular/common';
import { data } from 'jquery';
import { environment } from '../../../../../../environments/environment';
import { SalesPerMonthDTO } from '../../../../../models/salesPerMonthDTO';
import { NgApexchartsModule } from 'ng-apexcharts'; 

declare var ApexCharts: any;

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
  imports : [FormsModule, NgIf, CommonModule, NgApexchartsModule ],
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {
  startDate: string = '';
  endDate: string = '';
  salesStatistics: SalesStatistics | null = null;
  topSellingProducts: TopSellingProduct[] = [];
  purchaseTrend: PurchaseTrend = {};

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
      this.http.get<SalesPerMonthDTO[]>(`${this.apiUrl}/statistics/sales-per-month`)
    .subscribe(data => {
      this.chartData = data.map(item => item.totalQuantity);
      this.chartLabels = data.map(item => `Tháng ${item.month}`);
    });
  }

  fetchStatistics(): void {
    if (!this.startDate || !this.endDate) {
      alert("Vui lòng chọn ngày bắt đầu và ngày kết thúc.");
      return;
    }

    this.http.get<SalesStatistics>(`${this.apiUrl}/statistics/summary`, {
      params: { startDate: this.startDate, endDate: this.endDate }
    }).subscribe(data => this.salesStatistics = data);
    console.log(this.salesStatistics);

    this.http.get<TopSellingProduct[]>(`${this.apiUrl}/statistics/top-products`, {
      params: { startDate: this.startDate, endDate: this.endDate }
    }).subscribe(data => {
      this.topSellingProducts = data;
      this.renderTopProductsChart();
    });

    this.http.get<{ orderDate: string, totalSold: number }[]>(`${this.apiUrl}/statistics/trend`, {
    params: {
      startDate: this.startDate,
      endDate: this.endDate
    }
  }).subscribe(data => {
    const grouped: { [key: string]: number } = {};

    data.forEach(item => {
      if (!grouped[item.orderDate]) {
        grouped[item.orderDate] = 0;
      }
      grouped[item.orderDate] += item.totalSold;
    });

    this.purchaseTrend = grouped;
    this.renderTrendChart(); // Vẽ lại biểu đồ sau khi gom xong
  });

  }

  renderTrendChart() {
    const categories = Object.keys(this.purchaseTrend);
    const data = Object.values(this.purchaseTrend).map(Number);

    const options = {
      chart: {
        type: 'line',
        height: 300
      },
      series: [{
        name: "Sản phẩm đã bán",
        data: data
      }],
      xaxis: {
        categories: categories
      },
      title: {
        text: ""
      }
    };

    const chart = new ApexCharts(document.querySelector("#trend-chart"), options);
    chart.render();
  }

  renderTopProductsChart() {
    const categories = this.topSellingProducts.map(p => p.bookTitle);
    const data = this.topSellingProducts.map(p => p.totalSold);

    const options = {
      chart: {
        type: 'bar',
        height: 300
      },
      series: [{
        name: "Số lượng đã bán",
        data: data
      }],
      xaxis: {
        categories: categories
      },
      title: {
        text: ""
      }
    };

    const chart = new ApexCharts(document.querySelector("#top-products-chart"), options);
    chart.render();
  }


  exportStatistics(): void {
  if (!this.startDate || !this.endDate) {
    alert("Vui lòng chọn ngày bắt đầu và ngày kết thúc.");
    return;
  }

  this.http.get(`${this.apiUrl}/statistics/export`, {
    params: {
      startDate: this.startDate,
      endDate: this.endDate
    },
    responseType: 'blob'
    }).subscribe(blob => {
      const a = document.createElement('a');
      const objectUrl = URL.createObjectURL(blob);
      a.href = objectUrl;
      a.download = `thong_ke_${this.startDate}_den_${this.endDate}.xlsx`;
      a.click();
      URL.revokeObjectURL(objectUrl);
    }, err => {
      console.error("Xuất báo cáo thất bại:", err);
      alert("Xuất báo cáo thất bại!");
    });
  }


  chartData: number[] = [];
  chartLabels: string[] = [];


}
