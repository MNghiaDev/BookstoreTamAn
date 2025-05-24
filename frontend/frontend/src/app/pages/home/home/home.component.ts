import { Component } from '@angular/core';
import { BestSellingComponent } from "../elements/best-selling/best-selling.component";
import { FeaturedItemComponent } from "../elements/featured-item/featured-item.component";
import { PickedByAuthorComponent } from "../elements/picked-by-author/picked-by-author.component";
import { RouterLink, RouterOutlet } from '@angular/router';
import { HeaderComponent } from "../../../layout/header/header.component";
import { FooterComponent } from "../../../layout/footer/footer.component";
import { BannerComponent } from "../../../layout/banner/banner.component";

@Component({
  selector: 'app-home',
  imports: [BestSellingComponent, FeaturedItemComponent,
    PickedByAuthorComponent,
    RouterOutlet, HeaderComponent, FooterComponent, BannerComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
