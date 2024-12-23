export interface AppState {
  language: string;
  theme: string;
  user: UserState;
}

export interface UserState {
  token?: string;
}
