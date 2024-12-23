import { NgForOf } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'sec-animated-background',
  standalone: true,
  templateUrl: './animated-background.component.html',
  imports: [NgForOf],
  styleUrl: './animated-background.component.scss'
})
export class AnimatedBackgroundComponent {

  numbers: number[] = Array(64).fill(1);
}
