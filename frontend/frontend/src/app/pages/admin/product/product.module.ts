import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductRoutingModule } from './product-routing.module';
import { ProductListComponent } from './product-list/product-list.component';
import { ProductFormComponent } from './product-form/product-form.component';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    ProductRoutingModule
  ]
})
export class ProductModule { }
