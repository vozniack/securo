import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { TranslatePipe } from '@ngx-translate/core';
import { fadeInOutAnimation } from '../../shared/animations/fade-in-out-animation';
import { TextInputComponent } from '../../shared/components/forms/text-input/text-input.component';
import { UsersTableComponent } from './users-table/users-table.component';

@Component({
  selector: 'sec-users',
  standalone: true,
  imports: [TranslatePipe, TextInputComponent, UsersTableComponent],
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss',
  animations: [fadeInOutAnimation]
})
export class UsersComponent {

  filters!: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.filters = this.formBuilder.group({
      search: new FormControl(null),
    });
  }

  getControl(name: string): FormControl {
    return this.filters.get(name) as FormControl;
  }
}
