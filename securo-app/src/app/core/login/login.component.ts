import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { tap } from 'rxjs/operators';
import { fadeInOutAnimation } from '../../shared/animations/fade-in-out-animation';
import { ButtonComponent } from '../../shared/components/buttons/button/button.component';
import { InputComponent } from '../../shared/components/forms/input/input.component';
import { emailRegex } from '../../shared/const/regex.const';
import { ACTION_USER_LOGIN } from '../../store/app/app.actions';
import { LoginRequest, LoginResponse } from '../auth/auth.interface';
import { AuthService } from '../auth/auth.service';
import { AnimatedBackgroundComponent } from './animated-background/animated-background.component';

@Component({
  selector: 'sec-login',
  standalone: true,
  imports: [
    AnimatedBackgroundComponent,
    InputComponent,
    ButtonComponent
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
  animations: [fadeInOutAnimation]
})
export class LoginComponent {

  form!: FormGroup;

  constructor(private authService: AuthService, private store: Store, private router: Router, private formBuilder: FormBuilder) {
    this.form = this.formBuilder.group({
      email: new FormControl('admin@securo.com', [Validators.required, Validators.pattern(emailRegex)]),
      password: new FormControl('Admin123!', [Validators.required])
    });
  }

  login(): void {
    this.authService.login(this.form.getRawValue() as LoginRequest).pipe(
      tap((response: LoginResponse) => this.store.dispatch(ACTION_USER_LOGIN({user: {token: response.token}}))),
      tap(() => this.router.navigate([''])),
    ).subscribe();
  }

  getControl(controlName: string): FormControl {
    return this.form.get(controlName) as FormControl;
  }
}
