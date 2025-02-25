import { BreakpointObserver } from '@angular/cdk/layout';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, Input } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Store } from '@ngrx/store';
import { TranslatePipe } from '@ngx-translate/core';
import { catchError, tap } from 'rxjs/operators';
import { ResponsiveComponent } from '../../../../shared/common/responsive.component';
import { ButtonComponent } from '../../../../shared/components/buttons/button/button.component';
import { TextInputComponent } from '../../../../shared/components/forms/text-input/text-input.component';
import { handleError } from '../../../../shared/utils/request.utils';
import { isNotEmpty } from '../../../../shared/utils/syntax.utils';
import { ACTION_PROGRESS } from '../../../../store/app/app.actions';
import { User } from '../../../../domain/entity/user.interface';
import { UserService } from '../../../../domain/services/user.service';

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

  constructor(private formBuilder: FormBuilder, private userService: UserService, private store: Store, override breakpointObserver: BreakpointObserver) {
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
      catchError((response: HttpErrorResponse) => handleError(this.store, response))
    ).subscribe();
  }

  isValid(): boolean {
    return isNotEmpty(this.passwordForm.value.password) && isNotEmpty(this.passwordForm.value.passwordRepeat)
      && this.passwordForm.value.password === this.passwordForm.value.passwordRepeat;
  }

  getControl(name: string): FormControl {
    return this.passwordForm?.get(name) as FormControl;
  }
}
