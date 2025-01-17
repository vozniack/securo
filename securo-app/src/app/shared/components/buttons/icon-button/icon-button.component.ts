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

  @Input() size: 'tiny' | 'small' | 'normal' = 'normal';
  @Input() style: 'flat' | 'tonal' | 'filled' = 'flat'

  @Input() active: Boolean = false;
  @Input() hover: Boolean = true;
  @Input() routerLinkActive?: string;
}
