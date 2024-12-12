import {Component} from '@angular/core';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'sec-animated-background',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './animated-background.component.html',
  styleUrl: './animated-background.component.scss'
})
export class AnimatedBackgroundComponent {

  numbers: number[] = Array(64).fill(1);
}
