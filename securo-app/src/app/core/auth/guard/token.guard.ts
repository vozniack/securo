import {inject} from '@angular/core';
import {Router} from '@angular/router';
import {Store} from '@ngrx/store';
import {ACTION_USER_LOGOUT} from '../../../store/app/app.actions';
import {AuthService} from '../auth.service';

export const TokenGuard = () => {
  const authService = inject(AuthService);
  const store = inject(Store);
  const router = inject(Router);

  if (authService.token) {
    return true;
  } else {
    store.dispatch(ACTION_USER_LOGOUT());
    router.navigate(['login']).then();

    return false;
  }
};
