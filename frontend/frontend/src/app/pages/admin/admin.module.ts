import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminRoutingModule } from './admin-routing.module';
import { LayoutComponent } from './layout/layout.component'; // Import đúng

@NgModule({
  declarations: [],
  imports: [CommonModule, AdminRoutingModule],
})
export class AdminModule {}

