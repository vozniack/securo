import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { map } from 'rxjs/operators';
import { ACTION_USER_LOGIN, ACTION_USER_LOGOUT, } from './app.actions';

@Injectable()
export class AppEffects {

  constructor(private actions: Actions) {
  }

  logout$ = createEffect(() =>
    this.actions.pipe(
      ofType(ACTION_USER_LOGOUT.type),
      map(() => ACTION_USER_LOGIN({user: {}}))
    )
  );
}
