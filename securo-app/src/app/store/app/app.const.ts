import {AppState} from './app.state';

/* Initial app state */

export const initialAppState = (): AppState => {
  return {
    user: {
      token: undefined
    }
  };
};
