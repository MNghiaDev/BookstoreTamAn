import { Component } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { BestSellingComponent } from "./elements/best-selling/best-selling.component";
import { FeaturedItemComponent } from "./elements/featured-item/featured-item.component";
import { PickedByAuthorComponent } from "./elements/picked-by-author/picked-by-author.component";
import { TestimonialsComponent } from "./elements/testimonials/testimonials.component";
import { CallToActionComponent } from "./elements/call-to-action/call-to-action.component";
import { LatestNewsComponent } from "./elements/latest-news/latest-news.component";
import { FooterComponent } from "../footer/footer.component";
import { RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [HeaderComponent, BestSellingComponent, FeaturedItemComponent
    , PickedByAuthorComponent, TestimonialsComponent
    , CallToActionComponent, LatestNewsComponent, 
    FooterComponent, RouterOutlet],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
