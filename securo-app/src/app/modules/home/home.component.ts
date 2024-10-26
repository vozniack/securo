import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { fadeInOutAnimation } from '../../shared/animations/fade-in-out-animation';

@Component({
  selector: 'sec-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  animations: [fadeInOutAnimation]
})
export class HomeComponent {
}
