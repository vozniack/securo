import { Component, Input } from '@angular/core';

@Component({
  selector: 'sec-icon',
  standalone: true,
  imports: [],
  templateUrl: './icon.component.html',
  styleUrl: './icon.component.scss'
})
export class IconComponent {

  @Input()
  name!: string;

  @Input()
  size: 16 | 18 | 20 | 22 | 24 | 30 | 38 = 24;

  @Input()
  weight: 200 | 300 | 400 | 500 | 600 = 300;
}
