import { Component, Input } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { fadeInOutAnimation } from '../../../animations/fade-in-out-animation';
import { IconComponent } from '../../common/icon/icon.component';

@Component({
  selector: 'sec-switch',
  standalone: true,
  imports: [ReactiveFormsModule, IconComponent],
  templateUrl: './switch.component.html',
  styleUrl: './switch.component.scss',
  animations: [fadeInOutAnimation]
})
export class SwitchComponent {

  @Input() control: FormControl = new FormControl();

  @Input() iconOff?: string;
  @Input() iconOn?: string;

  getIcon(): string {
    switch (this.control.value) {
      case true:
        return this.iconOn ? this.iconOn : '';
      case false:
        return this.iconOff ? this.iconOff : '';
      default:
        return '';
    }
  }
}
