import { NgIf } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { IconComponent } from '../../common/icon/icon.component';

@Component({
  selector: 'sec-text-input',
  standalone: true,
  imports: [ReactiveFormsModule, IconComponent, NgIf],
  templateUrl: './text-input.component.html',
  styleUrl: './text-input.component.scss'
})
export class TextInputComponent {

  @Input() control: FormControl = new FormControl();

  @Input() id: string = '';
  @Input() name: string = '';

  @Input() type: string = 'text';

  @Input() placeholder: string = '';
  @Input() placeholderShrink = true;

  @Input() maxLength: number = 128;

  @Input() icon: string = '';

  @Input() password: boolean = false;
  @Input() autocomplete: string = 'on';

  @Input() width: string = '100%';
  @Input() maxWidth: string = '';

  @Input() style: 'tonal' | 'outlined' = 'outlined';

  switchVisibility(): void {
    this.type = this.type == 'password' ? 'text' : 'password';
  }
}
