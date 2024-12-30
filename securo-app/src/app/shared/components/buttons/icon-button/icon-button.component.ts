import { Component, Input } from '@angular/core';
import { RouterLinkActive } from '@angular/router';
import { IconComponent } from '../../common/icon/icon.component';

@Component({
  selector: 'sec-icon-button',
  standalone: true,
  imports: [IconComponent, RouterLinkActive],
  templateUrl: './icon-button.component.html',
  styleUrl: './icon-button.component.scss',
})
export class IconButtonComponent {

  @Input() icon!: string;

  @Input() size: 'small' | 'normal' = 'normal';

  @Input() active: Boolean = false;
  @Input() routerLinkActive?: string;
}
