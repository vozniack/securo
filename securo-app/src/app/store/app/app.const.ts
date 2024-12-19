import { AppState } from './app.state';

/* Initial app state */

export const initialAppState = (): AppState => {
  return {
    theme: 'brown-light',
    user: {
      token: undefined
    }
  };
};
