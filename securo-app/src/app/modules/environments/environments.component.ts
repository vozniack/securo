import { Component } from '@angular/core';
import { fadeInOutAnimation } from '../../shared/animations/fade-in-out-animation';

@Component({
  selector: 'sec-environments',
  standalone: true,
  imports: [],
  templateUrl: './environments.component.html',
  styleUrl: './environments.component.scss',
  animations: [fadeInOutAnimation]
})
export class EnvironmentsComponent {

}
