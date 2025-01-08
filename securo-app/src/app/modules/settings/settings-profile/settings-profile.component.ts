import { BreakpointObserver } from '@angular/cdk/layout';
import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { TranslatePipe } from '@ngx-translate/core';
import { tap } from 'rxjs/operators';
import { ResponsiveComponent } from '../../../shared/common/responsive.component';
import { ButtonComponent } from '../../../shared/components/buttons/button/button.component';
import { DateInputComponent } from '../../../shared/components/forms/date-input/date-input.component';
import { TextInputComponent } from '../../../shared/components/forms/text-input/text-input.component';
import { PhoneNumberValidator, PhonePrefixValidator } from '../../../shared/utils/validator.utils';
import { User } from '../../users/users.interface';
import { UsersService } from '../../users/users.service';

@Component({
  selector: 'sec-settings-profile',
  standalone: true,
  imports: [TranslatePipe, TextInputComponent, NgIf, ButtonComponent, DateInputComponent],
  templateUrl: './settings-profile.component.html',
  styleUrl: './settings-profile.component.scss'
})
export class SettingsProfileComponent extends ResponsiveComponent {

  user!: User;

  userForm!: FormGroup;

  constructor(private formBuilder: FormBuilder, private userService: UsersService, override breakpointObserver: BreakpointObserver) {
    super(breakpointObserver);

    this.userService.getLoggedUser().pipe(
      tap((response: User) => this.buildForm(this.user = response))
    ).subscribe();
  }

  buildForm(user: User): void {
    this.userForm = this.formBuilder.group({
      email: [{value: user.email, disabled: true}],
      phonePrefix: [user.phonePrefix, PhonePrefixValidator],
      phoneNumber: [user.phoneNumber, PhoneNumberValidator],
      firstName: [user.firstName, Validators.required],
      lastName: [user.lastName, Validators.required],
      dateOfBirth: [user.dateOfBirth, Validators.required],
      country: [user.country],
      city: [user.city],
      zip: [user.zip],
      street: [user.street],
      house: [user.house],
    });
  }

  updateProfile(): void {
    this.userService.updateUser(this.user.id, this.userForm.value).pipe(
      tap((response: User) => this.buildForm(this.user = response))
    ).subscribe();
  }

  getControl(form: FormGroup, name: string): FormControl {
    return form?.get(name) as FormControl;
  }
}
