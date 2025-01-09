import { AppState, UserState } from './app.state';
import { initialAppState } from './app.const';
import { Action, createReducer, on } from '@ngrx/store';
import { ACTION_LANGUAGE, ACTION_PROGRESS, ACTION_THEME, ACTION_USER_LOGIN, ACTION_USER_LOGOUT, } from './app.actions';

export const initialState = initialAppState();

export function appReducer(state: AppState = initialState, action: Action) {
  return _appReducer(state, action);
}

const _appReducer = createReducer(initialState,
  on(ACTION_PROGRESS, (state, newState) => onSetProgress(state, newState.progress)),
  on(ACTION_LANGUAGE, (state, newState) => onSetLanguage(state, newState.language)),
  on(ACTION_THEME, (state, newState) => onSetTheme(state, newState.theme)),
  on(ACTION_USER_LOGIN, (state, newState) => onSetUser(state, newState.user)),
  on(ACTION_USER_LOGOUT, (state) => onSetUser(state, {})),
);

function onSetProgress(state: AppState, progress: 'none' | 'loading' | 'success' | 'failure') {
  return {
    ...state,
    progress: progress
  };
}

function onSetLanguage(state: AppState, language: string) {
  return {
    ...state,
    language: language
  };
}

function onSetTheme(state: AppState, theme: string) {
  return {
    ...state,
    theme: theme
  };
}

function onSetUser(state: AppState, user: UserState) {
  return {
    ...state,
    user: user
  };
}
