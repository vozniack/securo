import { AppState } from './app.state';

/* Initial app state */

export const initialAppState = (): AppState => {
  return {
    theme: 'blue-light',
    user: {
      token: undefined
    }
  };
};
