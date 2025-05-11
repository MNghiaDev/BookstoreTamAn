import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StatisticsRoutingModule } from './statistics-routing.module';
import { FormsModule } from '@angular/forms';
import { StatisticsComponent } from './statistics/statistics.component';


@NgModule({
  declarations: [],
  imports: [CommonModule, StatisticsRoutingModule, FormsModule],
})
export class StatisticsModule { }
