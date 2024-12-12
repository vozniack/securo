import {AppState, UserState} from './app.state';
import {initialAppState} from './app.const';
import {Action, createReducer, on} from '@ngrx/store';
import {ACTION_USER_LOGIN, ACTION_USER_LOGOUT,} from './app.actions';

export const initialState = initialAppState();

export function appReducer(state: AppState = initialState, action: Action) {
  return _appReducer(state, action);
}

const _appReducer = createReducer(initialState,
  on(ACTION_USER_LOGIN, (state, newState) => onSetUser(state, newState.user)),
  on(ACTION_USER_LOGOUT, (state) => onSetUser(state, {})),
);

function onSetUser(state: AppState, user: UserState) {
  return {
    ...state,
    user: user
  };
}
