import { BreakpointObserver } from '@angular/cdk/layout';
import { NgIf } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import { TranslatePipe } from '@ngx-translate/core';
import { throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { ResponsiveComponent } from '../../../shared/common/responsive.component';
import { ButtonComponent } from '../../../shared/components/buttons/button/button.component';
import { DateInputComponent } from '../../../shared/components/forms/date-input/date-input.component';
import { TextInputComponent } from '../../../shared/components/forms/text-input/text-input.component';
import { PhoneNumberValidator, PhonePrefixValidator } from '../../../shared/utils/validator.utils';
import { ACTION_PROGRESS } from '../../../store/app/app.actions';
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

  constructor(private formBuilder: FormBuilder, private userService: UsersService, private store: Store, override breakpointObserver: BreakpointObserver) {
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
    this.store.dispatch(ACTION_PROGRESS({progress: 'loading'}));

    this.userService.updateUser(this.user.id, this.userForm.value).pipe(
      tap((response: User) => this.buildForm(this.user = response)),
      tap(() => this.store.dispatch(ACTION_PROGRESS({progress: 'success'}))),
      catchError((error: HttpErrorResponse) => this.handleError(error))
    ).subscribe();
  }

  getControl(form: FormGroup, name: string): FormControl {
    return form?.get(name) as FormControl;
  }

  private handleError(error: HttpErrorResponse) {
    this.store.dispatch(ACTION_PROGRESS({progress: 'failure'}));
    return throwError(() => new Error(error.message));
  }
}
