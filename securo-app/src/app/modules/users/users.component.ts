import { BreakpointObserver } from '@angular/cdk/layout';
import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { TranslatePipe } from '@ngx-translate/core';
import { fadeInOutAnimation } from '../../shared/animations/fade-in-out-animation';
import { ResponsiveComponent } from '../../shared/common/responsive.component';
import { TextInputComponent } from '../../shared/components/forms/text-input/text-input.component';
import { UsersListComponent } from './users-list/users-list.component';
import { UsersTableComponent } from './users-table/users-table.component';

@Component({
  selector: 'sec-users',
  standalone: true,
  imports: [TranslatePipe, TextInputComponent, UsersTableComponent, NgIf, UsersListComponent],
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss',
  animations: [fadeInOutAnimation]
})
export class UsersComponent extends ResponsiveComponent {

  filters!: FormGroup;

  constructor(private formBuilder: FormBuilder, override breakpointObserver: BreakpointObserver) {
    super(breakpointObserver);

    this.filters = this.formBuilder.group({
      search: new FormControl(null),
    });
  }

  getControl(name: string): FormControl {
    return this.filters.get(name) as FormControl;
  }
}
