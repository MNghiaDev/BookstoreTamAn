import { Component } from '@angular/core';
import { BestSellingComponent } from "./elements/best-selling/best-selling.component";
import { FeaturedItemComponent } from "./elements/featured-item/featured-item.component";
import { PickedByAuthorComponent } from "./elements/picked-by-author/picked-by-author.component";
import { RouterLink, RouterOutlet } from '@angular/router';
import { HeaderComponent } from "../header/header.component";
import { FooterComponent } from "../footer/footer.component";

@Component({
  selector: 'app-home',
  imports: [BestSellingComponent, FeaturedItemComponent,
    PickedByAuthorComponent,
    RouterOutlet, HeaderComponent, FooterComponent, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
