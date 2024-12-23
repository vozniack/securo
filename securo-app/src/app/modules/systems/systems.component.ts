import { Component } from '@angular/core';
import { TranslatePipe } from '@ngx-translate/core';
import { fadeInOutAnimation } from '../../shared/animations/fade-in-out-animation';

@Component({
  selector: 'sec-systems',
  standalone: true,
  imports: [TranslatePipe],
  templateUrl: './systems.component.html',
  styleUrl: './systems.component.scss',
  animations: [fadeInOutAnimation]
})
export class SystemsComponent {
}
