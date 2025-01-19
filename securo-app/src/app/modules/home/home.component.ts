import { Component } from '@angular/core';
import { TranslatePipe } from '@ngx-translate/core';
import { fadeInAnimation } from '../../shared/animations/fade-in-animation';

@Component({
  selector: 'sec-home',
  standalone: true,
  imports: [TranslatePipe],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  animations: [fadeInAnimation]
})
export class HomeComponent {
}
