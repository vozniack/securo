import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { fadeInOutAnimation } from '../../shared/animations/fade-in-out-animation';

@Component({
  selector: 'sec-systems',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './systems.component.html',
  styleUrl: './systems.component.scss',
  animations: [fadeInOutAnimation]
})
export class SystemsComponent {
}
