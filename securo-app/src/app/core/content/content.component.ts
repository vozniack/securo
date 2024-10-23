import { Component, Input } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'sec-content',
  standalone: true,
  imports: [
    RouterOutlet
  ],
  templateUrl: './content.component.html',
  styleUrl: './content.component.scss'
})
export class ContentComponent {

  @Input()
  logged!: boolean;
}
