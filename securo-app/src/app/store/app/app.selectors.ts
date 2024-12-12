import {createFeatureSelector, createSelector} from '@ngrx/store';

export const SELECT_APP_STATE = createSelector(
  createFeatureSelector<any>('securo-storage'), state => state
);

export const SELECT_USER_STATE = createSelector(
  SELECT_APP_STATE, state => state.user
);

export const SELECT_USER_TOKEN = createSelector(
  SELECT_USER_STATE, state => state.token
);
