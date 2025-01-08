import { Component, Input } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'sec-date-input',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './date-input.component.html',
  styleUrls: ['./date-input.component.scss']
})
export class DateInputComponent {

  @Input() control: FormControl = new FormControl();

  @Input() id: string = '';
  @Input() name: string = '';
  @Input() label: string = '';

  @Input() width: string = '100%';
  @Input() maxWidth: string = '';

  @Input() style: 'tonal' | 'outlined' = 'outlined';

  @Input() dateTimePicker: boolean = false;
}
