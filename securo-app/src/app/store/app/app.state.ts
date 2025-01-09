export interface AppState {
  progress: undefined | 'none' | 'loading' | 'success' | 'failure';
  language: string;
  theme: string;
  user: UserState;
}

export interface UserState {
  token?: string;
}
