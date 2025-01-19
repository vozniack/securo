import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { fadeInAnimation } from '../../shared/animations/fade-in-animation';

@Component({
  selector: 'sec-content',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './content.component.html',
  styleUrl: './content.component.scss',
  animations: [fadeInAnimation]
})
export class ContentComponent {
}
