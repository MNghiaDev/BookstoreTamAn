import { Component } from '@angular/core';
import { FooterComponent } from "../../layout/footer/footer.component";
import { HeaderComponent } from "../../layout/header/header.component";

@Component({
  selector: 'app-thank-you',
  imports: [FooterComponent, HeaderComponent],
  templateUrl: './thank-you.component.html',
  styleUrl: './thank-you.component.css'
})
export class ThankYouComponent {

}
