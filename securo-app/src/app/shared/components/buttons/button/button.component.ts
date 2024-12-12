import { NgIf } from '@angular/common';
import { Component, Input } from '@angular/core';
import { IconComponent } from '../../common/icon/icon.component';

@Component({
  selector: 'sec-button',
  standalone: true,
  imports: [
    IconComponent,
    NgIf
  ],
  templateUrl: './button.component.html',
  styleUrl: './button.component.scss'
})
export class ButtonComponent {

  @Input() disabled: boolean = false;

  @Input() text?: string;
  @Input() iconLeft?: string;
  @Input() iconRight?: string;

  @Input() size: 'small' | 'normal' = 'normal';
  @Input() style: 'filled' | 'tonal' | 'outlined' = 'filled';

  @Input() width: string = '100%';
  @Input() maxWidth: string = '';

  getIconSize(): 16 | 20 {
    return this.size === 'small' ? 16 : 20;
  }
}
