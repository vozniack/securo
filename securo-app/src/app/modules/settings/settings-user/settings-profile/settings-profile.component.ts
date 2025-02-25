import { BreakpointObserver } from '@angular/cdk/layout';
import { NgIf } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import { TranslatePipe } from '@ngx-translate/core';
import { catchError, tap } from 'rxjs/operators';
import { ResponsiveComponent } from '../../../../shared/common/responsive.component';
import { ButtonComponent } from '../../../../shared/components/buttons/button/button.component';
import { DateInputComponent } from '../../../../shared/components/forms/date-input/date-input.component';
import { TextInputComponent } from '../../../../shared/components/forms/text-input/text-input.component';
import { fadeInAnimation } from '../../../../shared/animations/fade-in-animation';
import { handleError } from '../../../../shared/utils/request.utils';
import { PhoneNumberValidator, PhonePrefixValidator } from '../../../../shared/utils/validator.utils';
import { ACTION_PROGRESS } from '../../../../store/app/app.actions';
import { User } from '../../../../domain/entity/user.interface';
import { UserService } from '../../../../domain/services/user.service';

@Component({
  selector: 'sec-settings-profile',
  standalone: true,
  imports: [TranslatePipe, TextInputComponent, NgIf, ButtonComponent, DateInputComponent],
  templateUrl: './settings-profile.component.html',
  styleUrl: './settings-profile.component.scss',
  animations: [fadeInAnimation]
})
export class SettingsProfileComponent extends ResponsiveComponent implements OnInit {

  @Input() user!: User;

  userForm!: FormGroup;

  constructor(private formBuilder: FormBuilder, private userService: UserService, private store: Store, override breakpointObserver: BreakpointObserver) {
    super(breakpointObserver);
  }

  ngOnInit(): void {
    this.buildForm(this.user);
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
      catchError((response: HttpErrorResponse) => handleError(this.store, response))
    ).subscribe();
  }

  getControl(name: string): FormControl {
    return this.userForm?.get(name) as FormControl;
  }
}
