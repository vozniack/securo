import { AppState } from './app.state';

/* Initial app state */

export const initialAppState = (): AppState => {
  return {
    progress: undefined,
    language: 'en',
    theme: 'blue-light',
    user: {
      token: undefined
    }
  };
};
