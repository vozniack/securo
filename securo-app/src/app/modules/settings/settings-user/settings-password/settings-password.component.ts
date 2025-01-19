import { BreakpointObserver } from '@angular/cdk/layout';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, Input } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Store } from '@ngrx/store';
import { TranslatePipe } from '@ngx-translate/core';
import { throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { ResponsiveComponent } from '../../../../shared/common/responsive.component';
import { ButtonComponent } from '../../../../shared/components/buttons/button/button.component';
import { TextInputComponent } from '../../../../shared/components/forms/text-input/text-input.component';
import { isNotEmpty } from '../../../../shared/utils/syntax.utils';
import { ACTION_PROGRESS } from '../../../../store/app/app.actions';
import { User } from '../../../users/users.interface';
import { UsersService } from '../../../users/users.service';

@Component({
  selector: 'sec-settings-password',
  standalone: true,
  imports: [TextInputComponent, TranslatePipe, ButtonComponent],
  templateUrl: './settings-password.component.html',
  styleUrl: './settings-password.component.scss'
})
export class SettingsPasswordComponent extends ResponsiveComponent {

  @Input() user!: User;

  passwordForm!: FormGroup;

  constructor(private formBuilder: FormBuilder, private userService: UsersService, private store: Store, override breakpointObserver: BreakpointObserver) {
    super(breakpointObserver);
    this.buildForm();
  }

  buildForm(): void {
    this.passwordForm = this.formBuilder.group({
      password: [''],
      passwordRepeat: [''],
    });
  }

  updatePassword(): void {
    this.store.dispatch(ACTION_PROGRESS({progress: 'loading'}));

    this.userService.updateUserPassword(this.user.id, {password: this.passwordForm.value.password}).pipe(
      tap(() => this.buildForm()),
      tap(() => this.store.dispatch(ACTION_PROGRESS({progress: 'success'}))),
      catchError((error: HttpErrorResponse) => this.handleError(error))
    ).subscribe();
  }

  isValid(): boolean {
    return isNotEmpty(this.passwordForm.value.password) && isNotEmpty(this.passwordForm.value.passwordRepeat)
      && this.passwordForm.value.password === this.passwordForm.value.passwordRepeat;
  }

  getControl(name: string): FormControl {
    return this.passwordForm?.get(name) as FormControl;
  }

  private handleError(error: HttpErrorResponse) {
    this.store.dispatch(ACTION_PROGRESS({progress: 'failure'}));
    return throwError(() => new Error(error.message));
  }
}
