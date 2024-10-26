import { Component, Input } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { fadeInOutAnimation } from '../../shared/animations/fade-in-out-animation';

@Component({
  selector: 'sec-content',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './content.component.html',
  styleUrl: './content.component.scss',
  animations: [fadeInOutAnimation]
})
export class ContentComponent {

  @Input()
  logged!: boolean;
}
