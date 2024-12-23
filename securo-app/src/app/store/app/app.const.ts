import { AppState } from './app.state';

/* Initial app state */

export const initialAppState = (): AppState => {
  return {
    language: 'en',
    theme: 'blue-light',
    user: {
      token: undefined
    }
  };
};
