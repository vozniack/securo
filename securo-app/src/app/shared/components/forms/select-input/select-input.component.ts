import { NgForOf, NgIf } from '@angular/common';
import { Component, ElementRef, HostListener, Input } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { fadeInAnimation } from '../../../animations/fade-in-animation';
import { IconComponent } from '../../common/icon/icon.component';
import { SelectInputOption } from './select-input.interface';

@Component({
  selector: 'sec-select-input',
  standalone: true,
  imports: [FormsModule, IconComponent, NgIf, ReactiveFormsModule, NgForOf],
  templateUrl: './select-input.component.html',
  styleUrl: './select-input.component.scss',
  animations: [fadeInAnimation]
})
export class SelectInputComponent {

  @Input() control: FormControl = new FormControl();

  @Input() options: SelectInputOption[] = [];

  @Input() id: string = '';
  @Input() type: string = 'text';

  @Input() name: string = '';
  @Input() placeholder: string = '';
  @Input() icon: string = '';

  @Input() width: string = '100%';
  @Input() maxWidth: string = '';

  @Input() size: 'small' | 'normal' = 'normal';
  @Input() style: 'tonal' | 'outlined' = 'outlined';

  @Input() allowEmpty: boolean = true;

  active = false;

  constructor(private elementRef: ElementRef) {
  }

  @HostListener('document:click', ['$event'])
  documentClickListener(event: any): void {
    this.active = !!this.elementRef.nativeElement.contains(event.target);
  }

  setValue(value: any): void {
    this.control.patchValue(value);
    setTimeout(() => this.active = false, 16);
  }

  getOptionName(): string | undefined {
    return this.options.find(option => option.value === this.control.value)?.name;
  }
}
