import { Component, Input } from '@angular/core';
import { IconComponent } from '../../common/icon/icon.component';

@Component({
  selector: 'sec-icon-button',
  standalone: true,
  imports: [
    IconComponent
  ],
  templateUrl: './icon-button.component.html',
  styleUrl: './icon-button.component.scss',
})
export class IconButtonComponent {

  @Input()
  icon!: string;

  @Input()
  active: Boolean = false;
}
