import { createAction, props } from '@ngrx/store';
import { UserState } from './app.state';

/* User */

export const ACTION_LANGUAGE = createAction(
  '[App][User] Set language', props<{ language: string }>()
);

export const ACTION_THEME = createAction(
  '[App][User] Set theme', props<{ theme: string }>()
);

export const ACTION_USER_LOGIN = createAction(
  '[App][User] Set user state', props<{ user: UserState }>()
);

export const ACTION_USER_LOGOUT = createAction(
  '[App][User] Logout'
);
